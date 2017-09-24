package br.com.aio.entity;

/**
 * Created by elton on 23/09/2017.
 */

public class FragmentoFactory {

    public enum NavegacaoEnum {
        INICIO("role");

        private String role;
        private String label;

        private NavegacaoEnum(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }

        public String getLabel() {
            return label;
        }
    }
}
