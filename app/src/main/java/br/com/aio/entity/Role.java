package br.com.aio.entity;

import java.util.List;

/**
 * Created by elton on 23/09/2017.
 */

public class Role {
    private Integer id;
    private String role;

    public Role(Integer id, String role){
        this.id = id;
        this.role = role;
    }

    public Role(String role){
        this.role = role;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public static boolean hasRole(List<Role> roles, FragmentoFactory.NavegacaoEnum navigation){
        if(navigation != null) {
            if(navigation.getRole() == null)
                return true;

            for (Role item : roles)
                if (item.getRole().equals(navigation.getRole()))
                    return true;
        }
        return false;
    }
}
