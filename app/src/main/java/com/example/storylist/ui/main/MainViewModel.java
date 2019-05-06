package com.example.storylist.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.storylist.model.StoryItems;
import com.example.storylist.repository.StoryRepository;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class MainViewModel extends ViewModel {

    private final StoryRepository mStoryRepository;

    private final MutableLiveData<StoryItems> mSelectedStory = new MutableLiveData<>();

    @Inject
    public MainViewModel(StoryRepository storyRepository) {
        this.mStoryRepository = storyRepository;
    }

    public Observable<List<StoryItems>> getStories() {
        return mStoryRepository.getStories();
    }

    public MutableLiveData<StoryItems> getSelectedPosition() {
        return mSelectedStory;
    }

    public void setSelectedItem(StoryItems storyItem) {
        mSelectedStory.postValue(storyItem);
    }


}
