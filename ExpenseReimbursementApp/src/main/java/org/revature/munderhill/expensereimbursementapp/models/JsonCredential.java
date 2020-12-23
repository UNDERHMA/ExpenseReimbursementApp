package org.revature.munderhill.expensereimbursementapp.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Setter
public class JsonCredential {
    private int accountId;
    private int userId;
    private String permissionName;
    public JsonCredential() {};
    public JsonCredential(int accountId, int userId, String permissionName) {
        this.accountId = accountId;
        this.userId = userId;
        this.permissionName = permissionName;
    }

    /*
        Converts object to JSON string using Jackson API's ObjectMapper class.
        Returns String and throws JsonProcessingException which is handled within
        the method.
     */
    public String makeJsonString() {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(this);
        } catch (JsonProcessingException j) {
            log.error("JSONProcessingException, JsonCredential", j);
        }
        return "";
    }
}
