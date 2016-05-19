package josh.land.meemeries.MemeBrowser.API.interfaces;

import java.util.List;

import josh.land.meemeries.MemeBrowser.API.models.Meme;

public interface IBackendLess {
    void onSuccess(List<Meme> memes);
    void onFailure();
}
