package sendMail;

import sendMail.util.MailSenderInfo;
import sendMail.util.SimpleMailSender;
//@Controller
//@RequestMapping("/system")
//@Scope("prototype")
public class sendMailTest {
	//@RequestMapping(value="/sendMail")
	//@ResponseBody
	 public String sendMail(){
		try {
		/*	String zh = request.getParameter("account");
			String email = request.getParameter("email");
			String ty = request.getParameter("ty");
			List list = this.getQzDao().execSqlQueryToArrays(
					"select useraccounttype,userid,userrealname from frmuser where useraccount='"
							+ zh + "' and useraccounttype ='" + ty + "'");
			if (list != null && list.size() > 0) {
				if (!email.contains(zh)) {
					this.writeJsMessage(response, "alert('请输入正确的邮箱!');");
					return null;
				}
				Object[] obj = (Object[]) list.get(0);
				Random random = new Random();
				int x = random.nextInt(899999);
				x = x + 100000;
				String randomPwd = "" + x;
				// 密码加密
				String pwd = randomPwd;
				if (GlobalNames.PASSWORD_MD5) {
					MacMD5 md = new MacMD5();
					pwd = md.CalcMD5(randomPwd);
				}
				this.getQzDao().execSqlUpdate(
						"update frmuser set userpasswd='" + pwd
								+ "' where useraccount='" + zh + "'");*/
				String dzxx = "jiaowu-key@zhbit.com";//发送邮箱
				String hz = "";
				String hzs = dzxx.split("@")[1];
				if (hzs.indexOf("qq") != -1) {
					hz = "smtp.qq.com";
				} else if (hzs.indexOf("126") != -1) {
					hz = "smtp.126.com";
				} else if (hzs.indexOf("163") != -1) {
					hz = "smtp.163.com";
				} else {
					hz = "smtp." + hzs;
				}
				// 设置邮箱----------------------------------------------------
				MailSenderInfo mailInfo = new MailSenderInfo();
				mailInfo.setMailServerHost(hz);
				mailInfo.setMailServerPort("25");
				mailInfo.setValidate(true);
				mailInfo.setUserName("jiaowu-key");//发送邮箱用户名
				mailInfo.setPassword("jiaowu1234");// 发送的邮箱密码
				mailInfo.setFromAddress(dzxx);//发送邮箱
				mailInfo.setToAddress("email");//收件人的邮箱
				mailInfo.setSubject("北京理工大学珠海学院师生密码重置"); // 邮件主题
				//String ahref = randomPwd;
				/*String nr = "亲爱的"
						+ obj[2].toString()
						+ "，您好：<br><br>&nbsp;&nbsp;您教务系统的新密码为:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ ahref + "<br><br>&nbsp;&nbsp;为了确保您的帐号安全，请及时登录系统更新密码"
						+ "<br><br>&nbsp;&nbsp;如非本人操作请及时向教务处反映。<br><br>";
				nr += "<font color='red'>请勿直接回复该邮件</font><br><div style='width: 500px' align='center'>北京理工大学珠海学院教务处</div>";
				*/
				mailInfo.setContent("邮件的文本内容");// 邮件的文本内容
				// ---------------------------发送
				// 邮件-------------------------------------------------
				SimpleMailSender mail = new SimpleMailSender();
				String[] fjpath = new String[2];
				mail.sendTextMailhtml(mailInfo, hz, "0", fjpath);
			//	this.writeJsMessage(response,"alert('发送成功!请登录您的邮箱获取密码');window.close();");

		//	} else {
				//this.writeJsMessage(response, "alert('请输入正确的教职工或学生账号!');");
		//	}
		} catch (Exception e) {
			e.printStackTrace();
		//	this.writeJsMessage(response, "alert('发送失败');");
		}
		return null;
	}
}
