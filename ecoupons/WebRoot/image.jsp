<%@ page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*" pageEncoding="UTF-8" %>
<%!Color getRandColor(int fc, int bc) {//给定范围获得随机颜色
  Random random = new Random();
  if (fc > 255)
   fc = 255;
  if (bc > 255)
   bc = 255;
  int r = fc + random.nextInt(bc - fc);
  int g = fc + random.nextInt(bc - fc);
  int b = fc + random.nextInt(bc - fc);
  return new Color(r, g, b);
 }%>
<%
 //设置页面不缓存
 response.setHeader("Pragma", "No-cache");
 response.setHeader("Cache-Control", "no-cache");
 response.setDateHeader("Expires", 0);

 // 在内存中创建图象
 int width = 80, height = 25;
 BufferedImage image = new BufferedImage(width, height,
   BufferedImage.TYPE_INT_RGB);

 // 获取图形上下文
 Graphics g = image.getGraphics();

 //生成随机类
 Random random = new Random();

 // 设定背景色
 g.setColor(getRandColor(200, 250));
 g.fillRect(0, 0, width, height);

 //设定字体
 g.setFont(new Font("Times New Roman", Font.BOLD, 18));

 //画边框
 g.setColor(new Color(0, 0, 0));
 g.drawRect(0, 0, width - 1, height - 1);

 // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
 g.setColor(getRandColor(160, 200));
 for (int i = 0; i < 155; i++) {
  int x = random.nextInt(width);
  int y = random.nextInt(height);
  int xl = random.nextInt(12);
  int yl = random.nextInt(12);
  g.drawLine(x, y, x + xl, y + yl);
 }

 // 取随机产生的认证码(4位数字)
 String sRand = "";
 for (int i = 0; i < 4; i++) {
  String rand = null;
  //随机生成数字或者字母
  if (random.nextInt(10) > 5) {
   rand = String.valueOf((char)(random.nextInt(10) + 48));
   //替换rand中的0为1
   if(rand.indexOf("0")!=-1){
		  rand=rand.replace("0","1");
	  }
 
  } else {
   rand = String.valueOf((char)(random.nextInt(26) + 65));
  // System.out.println(rand);
  //替换rand中的o和O 为1
   if(rand.indexOf("O")!=-1){
		  rand=rand.replace("O","1");
		 
	  }
   if(rand.indexOf("o")!=-1){
		  rand=rand.replace("o","1");
	  }
  
  }  
  sRand += rand;
  
  // 将认证码显示到图象中
  g.setColor(new Color(random.nextInt(80), random
    .nextInt(80), random.nextInt(80)));
  //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
  g.drawString(rand, 15 * i + 10, 16);
 }

 // 将认证码存入SESSION
 session.setAttribute("rand", sRand);

 // 图象生效
 g.dispose();

 // 输出图象到页面
 ImageIO.write(image, "JPEG", response.getOutputStream());
%>