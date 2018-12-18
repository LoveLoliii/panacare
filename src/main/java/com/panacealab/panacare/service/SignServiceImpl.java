package com.panacealab.panacare.service;

import com.panacealab.panacare.dao.LoginDao;
import com.panacealab.panacare.dao.SignDao;
import com.panacealab.panacare.entity.MailInfo;
import com.panacealab.panacare.entity.MailValidate;
import com.panacealab.panacare.entity.UserInfo;
import com.panacealab.panacare.utils.MailSendUtil;
import com.panacealab.panacare.utils.MailTool;
import com.panacealab.panacare.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;


@Service
public class SignServiceImpl implements SignService {
    private Logger logger = LoggerFactory.getLogger(SignServiceImpl.class);
    @Autowired
    private SignDao signDao;
    @Autowired
    private LoginDao loginDao;

    @Override
    public String getVerificationCode(String mail) {


        String code = "123456";
        String rs = "";

        //检查邮箱是否已注册
        List userList = loginDao.query("",mail);

        if(userList.size()>0){

        }else{
            //检查数据库中是否存在未过期的可用的验证码
            List result = signDao.query("mail_validate_mail", mail);
            if (result.size() > 0) {
                //取最新bothtime对比
                result.sort(Comparator.reverseOrder());


                //存在未使用验证码，对有效期进行检验
                logger.info("查询到数据库内有记录，验证有效性。");
                MailValidate mailValidate = (MailValidate) result.get(0);
                Long bothtime = Long.valueOf(mailValidate.getMail_validate_codeboth()), time_range = Long.valueOf(mailValidate.getMail_validate_time_range());
                Long now = System.currentTimeMillis() / 1000;
                Long remnant = bothtime + time_range - now;

                logger.info("remant:" + remnant);
                if (remnant > 0) {
                    if (remnant > 300) {
                        logger.info("记录未过期且有效时间大于5分钟，继续使用。");
                        //使用原验证码
                        code = mailValidate.getMail_validate_code();
                    } else {
                        //使用新验证码
                        logger.info("记录过期或有效时间小于5分钟，生成新验证码。");
                        code = MailSendUtil.getRandomCode6();
                        //save code
                        signDao.insert(code, mail, String.valueOf(System.currentTimeMillis() / 1000));
                    }
                } else {
                    logger.info("记录过期，生成新验证码。");
                    code = MailSendUtil.getRandomCode6();
                    //save code
                    signDao.insert(code, mail, String.valueOf(System.currentTimeMillis() / 1000));

                }
            }  else {
                logger.info("未查询到数据库内有记录，生成新验证码。");
                //使用新验证码
                code = MailSendUtil.getRandomCode6();
                //save code
                signDao.insert(code, mail, String.valueOf(System.currentTimeMillis() / 1000));
            }


            //send code
            MailInfo mailInfo = new MailInfo();
            Properties p = new Properties();
            String formName = "";
            String password = "";
            String host = "";
            try {
                p.load(MailTool.class.getResourceAsStream("/configure.properties"));
                formName = p.getProperty("panacare.mail");
                password = p.getProperty("panacare.key");
                host = p.getProperty("panacare.smtp", "smtp-mail.outlook.com");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                rs = "error";
            }
            mailInfo.setHost(host);
            mailInfo.setFormName(formName);
            mailInfo.setFormPassword(password);
            mailInfo.setReplayAddress(mail);
            mailInfo.setToAddress(mail);
            mailInfo.setSubject("panacare-邮箱验证");
            mailInfo.setContent("<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "尊敬的用户,您好：\n" +
                    "<p>&nbsp; &nbsp; 您本次邮件验证码如下，10分钟内有效，请及时输入。<br>\n" +
                    "<br>\n" +
                    "&nbsp; &nbsp;&nbsp; &nbsp;<strong>" + code + "</strong>\n" +
                    "<br>\n" +
                    "<br>\n" +
                    "&nbsp; &nbsp;为保证账号安全，请勿泄漏此验证码。<br>\n" +
                    "<br>\n" +
                    "<i>&nbsp; &nbsp;如果您并未申请账号，可能是其他用户误输入了您的邮箱地址。<br>\n" +
                    "&nbsp; &nbsp;请忽略此邮件，或与我们联系。<br>\n" +
                    "&nbsp; &nbsp;（这是一封自动发送的邮件，请不要直接回复）</i><br>\n" +
                    "&nbsp; &nbsp;来自panacea-lab。\n" +
                    "</p>\n" +
                    "</body>\n" +
                    "</html>");
            MailSendUtil.sendHtmlMail(mailInfo);
            rs = "success";
            return rs;
        }
            logger.info("用户邮箱已存在,{}",mail);
            rs = "用户邮箱已存在";
        return rs;

    }

    @Override
    public String verifyMail(String mail, String code) {
        //
        String rs="";
        //查看数据库中是否存在有效申请记录
        List result = signDao.query("mail_validate_mail", mail);
        if (result.size() > 0) {
            //取出最新记录
            result.sort(Comparator.reverseOrder());


            //存在未使用验证码，对有效期进行检验
            logger.info("查询到数据库内有记录，验证有效性。");
            MailValidate mailValidate = (MailValidate) result.get(0);
            Long bothtime = Long.valueOf(mailValidate.getMail_validate_codeboth()), time_range = Long.valueOf(mailValidate.getMail_validate_time_range());
            Long now = System.currentTimeMillis() / 1000;
            Long remnant = bothtime + time_range - now;

            logger.info("remant:" + remnant);
            if(mailValidate.getMail_validate_code().equals(code)) {
                if (remnant > 0) {
                    //验证码有效
                    logger.info("验证码有效，更改验证码状态");
                    //改变状态 返回成功信息
                    //TODO change state
                    signDao.update(0,mail);
                    rs = "542";
                    return rs;
                } else {
                    logger.info("验证码过期");
                    rs = "545";
                    return rs;
                }
            }else{
                rs = "544";
                return rs;
            }
        }  else {
            logger.info("未查询到数据库内有记录。");
            rs = "543";
            return rs;
        }

        //return rs;
    }

    @Override
    public String sign(UserInfo u, String mail, String code) {
        //检查邮箱是否已经被注册
        List<UserInfo> availableRs = signDao.checkMailAvailable(mail);
        if(availableRs.size()>0){
            return "548";
        }

        //验证邮箱
        String rs = verifyMail(mail,code);
        if("542".equals(rs)){
            //验证成功 写入用户信息到数据库
            //mybatis 存储 entity

            //mybatis 不能重载
            try {
                u.setUser_uniq_id(StringUtil.getUUID());
                int irs = signDao.insertUser(u);
                logger.info("insertUser的返回结果是：" + irs);
                rs = "547";
            }catch (Exception e){
                e.printStackTrace();
                logger.error("注册数据库错误：{}",e.getMessage());
                rs="546";//无法catch
            }

        }

        return rs;
    }
}
