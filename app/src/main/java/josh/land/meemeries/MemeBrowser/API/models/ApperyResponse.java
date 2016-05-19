package josh.land.meemeries.MemeBrowser.API.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Appery Response Object received after POST
 *
 * Created by Josh on 5/19/16.
 */
public class ApperyResponse {
    @Expose
    @SerializedName("_id")
    private String id;

    @Expose
    @SerializedName("_createdAt")
    private String createdAt;
}
