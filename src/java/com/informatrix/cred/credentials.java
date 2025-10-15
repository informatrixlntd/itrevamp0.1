/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.informatrix.cred;

/**
 *
 * @author prohit.bagde
 */
public class credentials {

//    public String uname = "it"; //root it
//    public String Password = "support"; //redhat support
//    public String jdbcUrl = "jdbc:mysql://172.236.172.179/informatrixit?zeroDateTimeBehavior=convertToNull";//localhost:3306 172.236.172.179
//    public String Emailpassword = "IBGAcc@2025";
//    public String fromEmail = "info@informatrixit.com";
//    public String[] ccEmail = {"support@informatrixlntd.com", " roshan.gayaki@informatrixit.com", " ankit.gaikwad@informatrixit.com"};

    
    
    
    public String uname = "root";//lntdleaduser
    public String Password = "redhat";//Prohit@123 IBGAcc2012
    public String jdbcUrl = "jdbc:mysql://localhost:3306/informatrixit";//localhost:1369
    public String fromEmail = "info@informatrixit.com";
    public String Emailpassword = "IBGAcc@2025";
    public String[] ccEmail = {"support@informatrixlntd.com", " roshan.gayaki@informatrixit.com", " ankit.gaikwad@informatrixit.com"};
    
    
    
    
    
    
//     public String uname = "root";//lntdleaduser
//    public String Password = "root";//Prohit@123 IBGAcc2012
//    public String jdbcUrl = "jdbc:mysql://localhost:3306/informatrixit";//localhost:1369
//    public String fromEmail = "info@informatrixit.com";
//    public String Emailpassword = "ighhezbopuxlneny";
//    public String[] ccEmail = {"support@informatrixlntd.com", " roshan.gayaki@informatrixit.com", " ankit.gaikwad@informatrixit.com"};
//    
    
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getEmailpassword() {
        return Emailpassword;
    }

    public void setEmailpassword(String Emailpassword) {
        this.Emailpassword = Emailpassword;
    }

    public String[] getCcEmail() {
        return ccEmail;
    }

    public void setCcEmail(String[] ccEmail) {
        this.ccEmail = ccEmail;
    }

    @Override
    public String toString() {
        return "Credentials{" + "uname=" + uname + ", Password=" + Password + ", jdbcUrl=" + jdbcUrl + ", fromEmail=" + fromEmail + ", Emailpassword=" + Emailpassword + ", ccEmail=" + ccEmail + '}';
    }

}
