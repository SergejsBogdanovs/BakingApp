package lv.st.sbogdano.bakingapp.data.database.entries;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "steps",
        foreignKeys = @ForeignKey(entity = RecipeEntry.class,
                parentColumns = "id",
                childColumns = "recipeId",
                onDelete = CASCADE))
public class StepEntry {

    @PrimaryKey
    private int id;
    private Integer stepId;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
    private int recipeId;

    // Room
    public StepEntry(int id, Integer stepId, String shortDescription, String description, String videoURL, String thumbnailURL, int recipeId) {
        this.id = id;
        this.stepId = stepId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
        this.recipeId = recipeId;
    }

    @Ignore
    public StepEntry(Integer stepId, String shortDescription, String description, String videoURL, String thumbnailURL, int recipeId) {
        this.stepId = stepId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
        this.recipeId = recipeId;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer id) {
        this.stepId = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

}
