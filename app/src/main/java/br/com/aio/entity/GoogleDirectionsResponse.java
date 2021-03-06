package br.com.aio.entity;

/**
 * Created by elton on 07/11/2017.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

public class GoogleDirectionsResponse implements Parcelable {
    private transient String rawJson;
    private List<Route> routes;

    public GoogleDirectionsResponse() {
    }

    public String getDuration() {
        ensureNotRaw();
        return routes.isEmpty() ? "1 min" : routes.get(0).legs.get(0).duration.text;
    }

    public String getDistance() {
        ensureNotRaw();
        if(routes.isEmpty()){
            return "Alguns metros";
        }
        String bairro = getBairro(routes.get(0).legs.get(0).end_address);
        String distancia = routes.get(0).legs.get(0).distance.text;
        return bairro+ " - " + distancia;
    }

    public Integer getDistanceMeters() {
        ensureNotRaw();
        return routes.isEmpty() ? 0 : routes.get(0).legs.get(0).distance.value;
    }

    private String getBairro(String endereco) {
        String[] enderecos = endereco.split(",");
        endereco = enderecos[1].substring(enderecos[1].lastIndexOf("-")+1,enderecos[1].length()).trim();
        return  endereco;
    }

    private void ensureNotRaw() {
        if (rawJson != null) {
            initFromRawJson();
        }
    }

    public static GoogleDirectionsResponse fromRawJson(String json) {
        GoogleDirectionsResponse result = new GoogleDirectionsResponse();
        result.rawJson = json;
        return result;
    }

    // <Parcelable>

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        if (rawJson == null) {
            throw new UnsupportedOperationException("Can't parcel object because raw json form is not available");
        }
        out.writeString(rawJson);
    }

    public static final Parcelable.Creator<GoogleDirectionsResponse> CREATOR = new Parcelable.Creator<GoogleDirectionsResponse>() {
        public GoogleDirectionsResponse createFromParcel(Parcel in) {
            return new GoogleDirectionsResponse(in);
        }

        public GoogleDirectionsResponse[] newArray(int size) {
            return new GoogleDirectionsResponse[size];
        }
    };

    private GoogleDirectionsResponse(Parcel in) {
        rawJson = in.readString();
        initFromRawJson();
    }

    private void initFromRawJson() {
        Gson gson = new Gson();
        GoogleDirectionsResponse aux = gson.fromJson(rawJson, GoogleDirectionsResponse.class);
        routes = aux.routes;
        rawJson = null;
    }

    // </Parcelable>
}