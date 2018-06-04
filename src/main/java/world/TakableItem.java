package world;


public class TakableItem extends RoomObject {
    private String takeText;
    private String dropText;


    public TakableItem(String name, String[] alternateNames, String touchText, String takeText, String dropText,
                       String examineText)
    {
        this(name, alternateNames, touchText, takeText, dropText, examineText, false);
    }


    public TakableItem(String name, String[] alternateNames, String touchText, String takeText, String dropText,
                       String examineText, boolean breakIfTouched)
    {
        super(name, alternateNames, touchText, examineText, breakIfTouched);
        this.takeText = takeText;
        this.dropText = dropText;
    }


    public String getTakeText() {
        return takeText;
    }


    public String getDropText() {
        return dropText;
    }
}
