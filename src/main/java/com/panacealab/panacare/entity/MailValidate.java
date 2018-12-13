package com.panacealab.panacare.entity;

//import javax.persistence.*;

/*@Entity
@Table(name = "mail_validate")*/
public class MailValidate  implements Comparable<MailValidate>{
    private Integer mail_validate_id;
    private String mail_validate_mail;
    private String mail_validate_code;
    private String mail_validate_codeboth;
    private String mail_validate_time_range;
    private String mail_validate_code_state;

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    public Integer getMail_validate_id() {
        return mail_validate_id;
    }

    public void setMail_validate_id(Integer mail_validate_id) {
        this.mail_validate_id = mail_validate_id;
    }

    public String getMail_validate_mail() {
        return mail_validate_mail;
    }

    public void setMail_validate_mail(String mail_validate_mail) {
        this.mail_validate_mail = mail_validate_mail;
    }

    public String getMail_validate_code() {
        return mail_validate_code;
    }

    public void setMail_validate_code(String mail_validate_code) {
        this.mail_validate_code = mail_validate_code;
    }

    public String getMail_validate_codeboth() {
        return mail_validate_codeboth;
    }

    public void setMail_validate_codeboth(String mail_validate_codeboth) {
        this.mail_validate_codeboth = mail_validate_codeboth;
    }

    public String getMail_validate_time_range() {
        return mail_validate_time_range;
    }

    public void setMail_validate_time_range(String mail_validate_time_range) {
        this.mail_validate_time_range = mail_validate_time_range;
    }

    public String getMail_validate_code_state() {
        return mail_validate_code_state;
    }

    public void setMail_validate_code_state(String mail_validate_code_state) {
        this.mail_validate_code_state = mail_validate_code_state;
    }

    @Override
    public int compareTo(MailValidate o) {
        if(this.mail_validate_id >= o.getMail_validate_id()){
            return 1;
        }
        return -1;
    }
}
