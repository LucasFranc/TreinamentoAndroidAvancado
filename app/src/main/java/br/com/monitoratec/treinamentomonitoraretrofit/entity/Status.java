package br.com.monitoratec.treinamentomonitoraretrofit.entity;

import com.google.gson.annotations.SerializedName;

import br.com.monitoratec.treinamentomonitoraretrofit.R;

/**
 * Entidade da API GitHub Status
 *
 *  @see <a>  https://status.github.com/api/last-message.json </a>
 *
 * Created by lucasfranco on 09/01/17.
 */

public class Status {

    @SerializedName("status")
    public Type type;
    private String body;
    private String created_on;

    public enum Type{
        NONE(R.color.colorPrimaryDark,R.string.loading),
        @SerializedName("good")
        GOOD(R.color.green, R.string.good),
        @SerializedName("minor")
        MINOR(R.color.orange, R.string.minor),
        @SerializedName("major")
        MAJOR(R.color.red, R.string.major);

        private int colorRes;
        private int messageRes;

        Type(int colorRes, int messageRes) {
            this.colorRes = colorRes;
            this.messageRes = messageRes;
        }
        public int getColorRes(){
            return colorRes;
        }
        public int getMessageRes() {return messageRes;}
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type= type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }
}
