package com.imscd.poc.authorities;

/**
 * Created by Christian Sperandio on 17/07/2016.
 */
final public class CompanyUser {
    private String companyApiKey;
    private String application;
    private String login;

    public CompanyUser(String companyApiKey, String application, String login) {
        this.companyApiKey = companyApiKey;
        this.application = application;
        this.login = login;
    }

    public String getCompanyApiKey() {
        return companyApiKey;
    }

    public String getLogin() {
        return login;
    }

    public String getApplication() {
        return application;
    }


    @Override
    public int hashCode() {
        return companyApiKey.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CompanyUser) {
            CompanyUser o = (CompanyUser) obj;
            return (o.companyApiKey.equals(companyApiKey) &&
                    o.application.equals(application) &&
                    o.login.equals(login));
        } else {
            return false;
        }
    }

}
