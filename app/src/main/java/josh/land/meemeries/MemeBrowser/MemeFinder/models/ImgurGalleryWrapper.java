package josh.land.meemeries.MemeBrowser.MemeFinder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ImgurGalleryWrapper {
    @SerializedName("data")
    @Expose
    private List<ImgurGallery> data = new ArrayList<ImgurGallery>();
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<ImgurGallery> getData() {
        return data;
    }

    public ImgurGalleryWrapper withData(List<ImgurGallery> data) {
        this.data = data;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public ImgurGalleryWrapper withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ImgurGalleryWrapper withStatus(Integer status) {
        this.status = status;
        return this;
    }
}
