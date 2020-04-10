package com.citylive.server.otp;

import java.util.Map;

public class EmailTemplate {
    private String template;

    public EmailTemplate() {

        this.template = "Hi {{user}},\n" +
                "\n" +
                "Your Otp Number for CityLive sign up is {{otpnum}}.\n" +
                "\n" +
                "Thanks,\n" +
                "Team CityLive\n" +
                "\n";
    }

    public String getTemplate(Map<String, String> replacements) {
        String cTemplate = this.template;
        //Replace the String
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            cTemplate = cTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return cTemplate;
    }
}