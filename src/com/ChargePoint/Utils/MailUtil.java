package com.ChargePoint.Utils;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
 
 
/**
 * <p>Description:use the javamail to send email!Must use sun's mail.jar,no apache jar. </p>
 */
public class MailUtil {
 
    /**发送邮件
     * @param sendTo 收件人
     * @param subject 邮件主题
     * @param contents 内容（HTML）
     * @return boolean
     */
    public static boolean sendEmail(String sendTo,String subject,String contents) {
//    	MailUtil sendMail = new MailUtil("yihang.lv@chinajune.com", "364620185@qq.com", "364620185@qq.com",
//                "hmzzrvhxcsxubgfh", "测试Java Mail", "<a href='localhost:8080'>您好,Javamail!!请尽快完成注册</a>");
        // 增加2个附件
//        sendMail.addAttachment("D://HelloWorld.xml");
//        sendMail.addAttachment("D://IOTest.java");
    	
    	MailUtil sendMail = new MailUtil(sendTo, "364620185@qq.com", "364620185@qq.com",
              "hmzzrvhxcsxubgfh", subject, contents);
    	
        if (sendMail.send()) {
            return true;
        }else{
        	return false;
        }
    }
 
    private String to;          //接收人
    private String from;        //发送人
    private String userName;    
    private String password;
    private String subject;
    private String content;
 
    // 记录所有附件文件的集合,发送邮件的附件
    List<String> attachments = new ArrayList<String>();
 
    /**发送邮件方法
     * @param String to, String from, String userName, String password, String subject,String content
     * @param to 接收人
     * @param from 发送人
     * @param userName 账户
     * @param password 密码（授权码）
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public MailUtil(String to, String from, String userName, String password, String subject,
            String content) {
        this.to = to;
        this.from = from;
        this.userName = userName;
        this.password = password;
        this.subject = subject;
        this.content = content;
    }
 
    //将字符串转换为中文,否则标题会发生乱码现象,QQ邮箱为UTF-8.用GBK.GB2312都会乱码.
    public static String translateChinese(String strText) {
        try {
            // MimeUtility.encodeText(String text, String charset, String
            // encoding) throws java.io.UnsupportedEncodingException
            // text 头值 . charset 字符集。如果此参数为 null，则使用平台的默认字符集。
            // encoding 要使用的编码。当前支持的值为 "B" 和 "Q"。如果此参数为 null，则在大部分字符使用 ASCII
            // 字符集编码时使用 "Q" 编码；其他情况使用 "B" 编码。
            strText = MimeUtility.encodeText(new String(strText.getBytes(), "UTF-8"), "UTF-8", "B");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strText;
    }
 
    //增加附件
    public void addAttachment(String fname){
        attachments.add(fname);
    }
     
    public boolean send(){
        //创建邮件Session所需的Properties对象.API建议使用set而不是put(putall).
        Properties props=new Properties();
     // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        //SSL 加密
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //qq邮件发送端口
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        
        //创建Session对象,代表JavaMail中的一次邮件会话.
        //Authenticator==>Java mail的身份验证,如QQ邮箱是需要验证的.所以需要用户名,密码.
        //PasswordAuthentication==>系统的密码验证.内部类获取,或者干脆写个静态类也可以.
        Session session=Session.getDefaultInstance(props, 
                new Authenticator() {
                        public PasswordAuthentication getPasswordAuthentication(){
                            return new PasswordAuthentication(userName, password);
                        }
                    }
                );
         
        try {
            //构造MimeMessage并设置相关属性值,MimeMessage就是实际的电子邮件对象.
            MimeMessage msg=new MimeMessage(session);
            
            // 启动JavaMail调试功能，可以返回与SMTP服务器交互的命令信息  
            // 可以从控制台中看一下服务器的响应信息  
            session.setDebug(true);
            
            //设置发件人
            msg.setFrom(new InternetAddress(from));
            //设置收件人,为数组,可输入多个地址.
            InternetAddress[] addresses={new InternetAddress(to)};
            //Message.RecipientType==>TO(主要接收人),CC(抄送),BCC(密件抄送)
            msg.setRecipients(Message.RecipientType.TO, addresses);
            //设置邮件主题,如果不是UTF-8就要转换下.
            //subject=translateChinese(subject);
            msg.setSubject(subject);
             
            //=====================正文部分===========
            //构造Multipart容器
//            Multipart mp=new MimeMultipart();
            //=====================正文文字部分===========
            //向Multipart添加正文
//            MimeBodyPart mbpContent=new MimeBodyPart();
//            mbpContent.setText(content);
            //将BodyPart添加到MultiPart容器中
//            mp.addBodyPart(mbpContent);
             
            //=====================正文附件部分===========
            //向MulitPart添加附件,遍历附件列表,将所有文件添加到邮件消息里
//            for(String efile:attachments){
//                MimeBodyPart mbpFile=new MimeBodyPart();
//                //以文件名创建FileDataSource对象
//                FileDataSource fds=new FileDataSource(efile);
//                //处理附件
//                mbpFile.setDataHandler(new DataHandler(fds));
//                mbpFile.setFileName(fds.getName());
//                //将BodyPart添加到Multipart容器中
//                mp.addBodyPart(mbpFile);
//            }
            attachments.clear();
            //向MimeMessage添加Multipart

            msg.setContent(this.content, "text/html;charset=GBK");
//            设置头信息为base64编码防止邮件内容乱码
            msg.setHeader("Content-Transfer-Encoding", "base64");	
            msg.setSentDate(new Date());
            //发送邮件,使用如下方法!
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
     
     
}