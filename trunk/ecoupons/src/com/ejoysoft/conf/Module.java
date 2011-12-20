package com.ejoysoft.conf;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.Vector;

import com.ejoysoft.common.Format;
import com.ejoysoft.common.Globa;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-4-13
 * Time: 13:33:26
 * To change this template use Options | File Templates.
 */
public class Module {

    private String code;//事务编码
    private String name;    //名称
    private String url;     //URL访问地址
    private int number;     //事务排序号
    private String target;  //链接方式
    private String type;    //类型    right
    private Module parent;  //父节点
    private Vector children = new Vector();    //子节点向量
    private int intLevel;     //级别，用于树状显示

    private String state;//事务编码
    /**
     * 构造
     * @param code
     * @param name
     * @param url
     * @param type
     * @param parent
     */
    public Module(String code,String name, String url,String target, String type, Module parent) {
        this.code = code;
        this.name = name;
        this.url = url;
        this.target = target;
        this.type = type;
        this.parent = parent;
    }

    /**
     * 构造，加载自身信息和所有子节点的信息
     * @param eleModule 代表本身的Element
     * @param parent
     */
    public Module(Element eleModule, Module parent) {
        //加载本身信息
        this.code = eleModule.getAttribute("code");
        this.name = eleModule.getAttribute("name");
        this.url = eleModule.getAttribute("url");
        this.target = eleModule.getAttribute("target");
        this.state = eleModule.getAttribute("state");

        if (eleModule.getTagName().equals("module"))
            this.type = "module";
        else if (eleModule.getTagName().equals("affair"))
            this.type = "affair";
        this.parent = parent;
        //遍历加载子节点信息
        NodeList nlModule = eleModule.getElementsByTagName("module");   //module子节点
        if (nlModule != null) {
            for (int i = 0; i < nlModule.getLength(); i++) {
                if (((Element)nlModule.item(i).getParentNode()).getAttribute("name").equals(this.name))
                    this.children.add(new Module((Element)nlModule.item(i), this));
            }
        }
        NodeList nlAffair = eleModule.getElementsByTagName("affair");   //affair子节点
        if (nlAffair != null) {
            for (int i = 0; i < nlAffair.getLength(); i++) {
                if (((Element)nlAffair.item(i).getParentNode()).getAttribute("name").equals(this.name))
                    this.children.add(new Module((Element)nlAffair.item(i), this));
            }
        }
    }

    /**
     * 加载所有子节点信息，按顺序形成树状目录
     * @param allChild 保存所有子节点的向量
     * @param intLevel 子节点的级别
     */
    public void loadAllChild(Vector allChild, int intLevel) {
        for (int i = 0; i < this.children.size(); i++) {
            ((Module)this.children.get(i)).setIntLevel(intLevel);
            allChild.add(this.children.get(i));
            ((Module)this.children.get(i)).loadAllChild(allChild, intLevel + 1);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Module getParent() {
        return parent;
    }

    public void setParent(Module parent) {
        this.parent = parent;
    }

    public Vector getChildren() {
        return children;
    }

    public void setChildren(Vector children) {
        this.children = children;
    }

    public int getIntLevel() {
        return intLevel;
    }

    public void setIntLevel(int intLevel) {
        this.intLevel = intLevel;
    }

    public boolean haveChild() {
        return (this.children.size() > 0);
    }

    /**
     * 返回模块的全名称
     * 格式采用“上级模块/次上级模块/../本模块”
     * @return
     */
    public String getTotalName() {
        String totalName = this.name;
        Module parent = this.parent;
        while (parent != null) {
            totalName = parent.name + "/" + totalName;
            parent = parent.parent;
        }
        return totalName;
    }
     public String getAllParentName() {
        String totalName ="";
        Module parent = this.parent;
        while (parent != null) {
            totalName = parent.name + "<font color='#FF0000'>／</font>" + totalName;
            parent = parent.parent;
        }
        return totalName;
    }
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getState() {
        return Format.removeNull(state);
    }

    public void setState(String state) {
        this.state = state;
    }
}