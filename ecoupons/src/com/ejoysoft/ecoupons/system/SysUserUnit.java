package com.ejoysoft.ecoupons.system;

import com.ejoysoft.common.DbConnect;
import com.ejoysoft.common.exception.UserUnitIdException;

import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 系统中所有的用户单位/组（组织机构�?
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-4-10
 * Time: 11:33:23
 * To change this template use Options | File Templates.
 */
public class SysUserUnit {
    private static HashMap userUnits = new HashMap();     //保存所有的用户组
    private static Vector rootUserGroups = new Vector();   //保存所有的顶级用户组
    private static Vector userGroupTree = new Vector();     //以树形结构保存所有的用户组

    //构�?�方�?
    public SysUserUnit() {
    }

    public static void init(String tWhere) {
        try {
        	//query db
            String sql = "SELECT * FROM t_sy_unit  " + tWhere + "  ORDER BY strParentId,intSort";
            Connection con = DbConnect.getStaticCon();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //new a user group and set values
                Unit ug = new Unit();
                ug = ug.load(rs, false);
                //add into hashmap
                userUnits.put(ug.getStrId(), ug);
            }
            rs.close();
            stmt.close();
            con.close();
            //建立用户组之间的关系
            Iterator ite = userUnits.keySet().iterator();
            while (ite.hasNext()) {
                Unit ug = (Unit) userUnits.get(ite.next());
                //add into vector if it's root user group
                //else relate it with its parent user group
                if (ug.getStrParentId().equals("")) {
                    rootUserGroups.add(ug);
                } else {
                    ((Unit) userUnits.get(ug.getStrParentId())).addChild(ug);
                }
            }
            //加载树形结构
            loadUserGroupTree(userGroupTree);
            System.out.println("[INFO]:SysUserUnit Initialized Successful");
        } catch (SQLException e) {
            System.out.println("[ERROR]:An error occured in SysUserUnit.init()");
            e.printStackTrace();
        }
    }

    /**
     * 加载�?有的用户组信息，并保存到用户组树形向量中
     * @param userGroupTree 用户组树形向�?
     */
    private static void loadUserGroupTree2(Vector userGroupTree) {
        //遍历顶级用户组，加载用户组信�?
        for (int i = 0; i < rootUserGroups.size(); i++) {
            //加入顶级用户�?
            Unit theUserGroup = (Unit) rootUserGroups.get(i);
            theUserGroup.setIntLevel(1);
            userGroupTree.add(theUserGroup);
            //加载下级用户�?
            theUserGroup.loadChildren(userGroupTree);
        }
    }

    /**
     * 加载�?有的用户组信息，并保存到用户组树形向量中
     * @param userGroupTree 用户组树形向�?
     */
    private static void loadUserGroupTree(Vector userGroupTree) {
//        // this.rootUserGroups重新排序
        SortVector sv = new SortVector(new Unit.UnitCompare());
        for (int i = 0; i < rootUserGroups.size(); i++) {
            sv.add((Unit) rootUserGroups.get(i));
        }
        sv.sort();
        rootUserGroups = sv;
        //遍历顶级用户组，加载用户组信�?
        for (int i = 0; i < rootUserGroups.size(); i++) {
            //加入顶级用户�?
            Unit unit0 = (Unit) rootUserGroups.get(i);
            unit0.setIntLevel(1);
            userGroupTree.add(unit0);
            //加载下级用户�?
            unit0.loadChildren(userGroupTree);
        }
    }

    public static Vector getUserGroupTree() {
        return userGroupTree;
    }

    /**
     * 重新加载参数信息
     * @throws SQLException
     */
    public static void reLoad(String tWhere) throws SQLException {
        userUnits.clear();
        rootUserGroups.clear();
        userGroupTree.clear();
        init(tWhere);
    }

    /**
     * 验证用户组Id是否符合修改的条�?
     * 不能允许出现死循环的现象
     * @param id 修改的用户组的id
     * @param parentId 修改的用户组的上级组的id
     * @throws UserUnitIdException
     */
    public static void validateId(String id, String parentId) throws UserUnitIdException {
        if (parentId.equals(""))
            return;
        if (id.equals(parentId))
            throw new UserUnitIdException("一个机构不能把自身作为上级机构");

        Unit ug = (Unit) userUnits.get(parentId);
        while (ug!=null&&!ug.getStrParentId().equals("")) {
            if (ug.getStrParentId().equals(id))
                throw new UserUnitIdException("一个机构不能选择自身的下级机构作为上级机构");
            else
                ug = (Unit) userUnits.get(ug.getStrParentId());
        }
    }

    /**
     * 验证是否符合删除条件
     * 如果用户组有下级组或用户，则不允许删�?
     * @param id 待删除的用户组ID
     * @throws UserUnitIdException
     * @throws SQLException
     */
    public static void validateDelId(String id) throws UserUnitIdException, SQLException {
        //验证是否有下级组
        if (((Unit) userUnits.get(id)).haveChild())
            throw new UserUnitIdException("不能删除还有下级机构的上级机构", "请先删除下级机构");
        //验证是否有用�?
        String sql = "SELECT * FROM t_sy_user WHERE strUnitId='" + id + "'";
        Connection con = DbConnect.getStaticCon();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            rs.close();
            stmt.close();
            con.close();
            throw new UserUnitIdException("不能删除还有用户的机构", "请先删除机构内的用户");
        }
        rs.close();
        stmt.close();
        con.close();
    }

    /**
     * 初始化参数信�?
     * @throws java.sql.SQLException
     */
    public void clear() throws SQLException {
        userUnits.clear();
        rootUserGroups.clear();
        userGroupTree.clear();
    }

    /**
     * 返回指定用户组的全称，格式为：省委办公厅/文书�?/办文�?
     * @param userUnitId
     * @return
     */
    public static String getTotalName(String userUnitId) {
        if (userUnitId == null || userUnitId.equals("")) return "";
        Unit ug = (Unit) userUnits.get(userUnitId);
        String totalName = "";
        if (ug != null) {
            totalName = ug.getStrUnitName();
            while (!ug.getStrParentId().equals("")) {
                ug = (Unit) userUnits.get(ug.getStrParentId());
                totalName = ug.getStrUnitName() + "／" + totalName;
            }
        }
        return totalName;
    }

    //获取单位名称
    public static String getUnitName(String userUnitId) {
        if (userUnitId == null || userUnitId.equals("")) return "";
        Unit ug = (Unit) userUnits.get(userUnitId);
        if (ug != null)
            return ug.getStrUnitName();
        return "";
    }

    //根据机构id数组返回机构名称
    public static String UnitNames(String[] arrUnitId) {
        String uNames = "";
        if (arrUnitId == null || arrUnitId.length <= 0)
            return "";
        else {
            Unit ug = (Unit) userUnits.get(arrUnitId[0]);
            uNames = ug.getStrUnitName();
            for (int i = 1; i < arrUnitId.length; i++) {
                ug = (Unit) userUnits.get(arrUnitId[i]);
                uNames = uNames + "、" + ug.getStrUnitName();
            }
        }
        return uNames;
    }

    public static Unit getUserGroup(String userGroupId) {
        return (Unit) userUnits.get(userGroupId);
    }

    /**
     * 返回指定用户�?在组的全路径，strParentId �? strRootUnitCode   的所有id集合�? 格式为：'334'�?'4443'�?'555'�?
     * @param userUnitId
     * @return
     */
    public static String getUnitGroupId(String userUnitId) {
        if (userUnitId.equals("")) return "";
        Unit ug = (Unit) userUnits.get(userUnitId);
        String strUnitIds = "'" + userUnitId + "'";
        if (ug != null) {
            strUnitIds = "'" + ug.getStrId() + "'";
            while (!ug.getStrParentId().equals("")) {
                ug = (Unit) userUnits.get(ug.getStrParentId());
                strUnitIds = "'" + ug.getStrId() + "'," + strUnitIds;
            }
        }
        return strUnitIds;
    }

    public static String getAllUnitIds(String userUnitId) {
        if (userUnitId.equals("")) return "";
        Unit ug = (Unit) userUnits.get(userUnitId);
        String strUnitIds = userUnitId;
        if (ug != null) {
            strUnitIds = ug.getStrId();
            while (!ug.getStrParentId().equals("")) {
                ug = (Unit) userUnits.get(ug.getStrParentId());
                strUnitIds = ug.getStrId() + "," + strUnitIds;
            }
        }
        return strUnitIds;
    }

    public static String getAllRootUnitIds(String userUnitId) {
        if (userUnitId.equals("")) return "";
        Unit ug = (Unit) userUnits.get(userUnitId);
        String strUnitIds = userUnitId;
        if (ug != null) {
            strUnitIds = ug.getStrId();
            while (!ug.getStrParentId().equals("")) {
                ug = (Unit) userUnits.get(ug.getStrParentId());
                strUnitIds = ug.getStrId() + "," + strUnitIds;
            }
        }
        return strUnitIds;
    }

    //根据�?个UNITID 返回用户的独立单位ID
    public static String getRootId(String unitId) {
        if (unitId.equals("")) return "";
        Unit ug = (Unit) userUnits.get(unitId);
        String strRootId = unitId;
        if (ug != null) {
            strRootId = ug.getStrId();
            while (!ug.getStrParentId().equals("")) {
                ug = (Unit) userUnits.get(ug.getStrParentId());
                strRootId = ug.getStrId();
            }
        }
        return strRootId;
    }

    //根据�?个UNITID 返回用户的单位父节点ID
    public static String getParentId(String unitId) {
        if (unitId.equals("")) return "";
        Unit ug = (Unit) userUnits.get(unitId);
        return (ug != null ? ug.getStrParentId() : "");
    }

    public static int getMaxSort(String parentId) {
        if (parentId.equals(""))
            return rootUserGroups.size();
        else
            return getUserGroup(parentId).getChildren().size();
    }
    
    /**
     * 判断strChildId代表的机构是否归strParentId代表的机构管�?
     * @param strParentId
     * @param strChildId
     * @return
     */
    public static boolean isManaged(String strParentId, String strChildId) {
    	if (strParentId.equals(strChildId)) {
    		return true;
    	} else {
    		Unit child = (Unit)userUnits.get(strChildId);
    		while (!child.getStrParentId().equals("")) {
    			if (child.getStrParentId().equals(strParentId))
    				return true;
    			child = (Unit)userUnits.get(child.getStrParentId());
    		}
    		return false;
    	}
    }
    
    public static Unit getUnitByDistrict(String strDistrictId) {
    	for (int i = 0; i < userGroupTree.size(); i++) {
    		Unit u = (Unit)userGroupTree.get(i);
    		if (u.getStrDistrictId().equals(strDistrictId))
    			return u;
    	}
    	return null;
    }

	public static Unit getUnitByName(String strName) {
		for (int i = 0; i < userGroupTree.size(); i++) {
    		Unit u = (Unit)userGroupTree.get(i);
    		if (u.getStrUnitName().equals(strName))
    			return u;
    	}
    	return null;
	}
	
	public static Vector<Unit> getAllUnitManaged(String strUnitId) {
		Vector<Unit> vctUnit = new Vector<Unit>();
		int intLevel = 999;
		for (int i = 0; i < userGroupTree.size(); i++) {
			Unit u = (Unit)userGroupTree.get(i);
			if (u.getStrId().equals(strUnitId)) {
				vctUnit.add(u);
				intLevel = u.getIntLevel();
				for (int j = i + 1; j < userGroupTree.size(); j++) {
					u = (Unit)userGroupTree.get(j);
					if (u.getIntLevel() > intLevel) {
						vctUnit.add(u);
					} else {
						break;
					}
				}
				break;
			}
		}
		return vctUnit;
	}
	public static Vector<Unit> getAllUnitd() {
		Vector<Unit> vctUnit = new Vector<Unit>();
		int intLevel = 999;
		for (int i = 0; i < userGroupTree.size(); i++) {
			Unit u = (Unit)userGroupTree.get(i);
			//if (u.getStrId().equals(strUnitId)) {
				vctUnit.add(u);
				intLevel = u.getIntLevel();
				for (int j = i + 1; j < userGroupTree.size(); j++) {
					u = (Unit)userGroupTree.get(j);
					if (u.getIntLevel() > intLevel) {
						vctUnit.add(u);
					} else {
						break;
					}
				}
				break;
			//}
		}
		return vctUnit;
	}
	public static String getAllUnitIdManaged(String strUnitId) {
		Vector<Unit> vctUnit = getAllUnitManaged(strUnitId);
		String result = "";
		for (int i = 0; i < vctUnit.size(); i++) {
			result += ((Unit)vctUnit.get(i)).getStrId() + ",";
		}
		if (result.length() > 0)
			result = result.substring(0, result.length() - 1);
		return result;
	}
}