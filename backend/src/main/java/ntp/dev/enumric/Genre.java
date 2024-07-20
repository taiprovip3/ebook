package ntp.dev.enumric;

public enum Genre {
	FICTION("Fiction"),
    NON_FICTION("Non-fiction"),
    MYSTERY("Mystery"),
    THRILLER("Thriller"),
    FANTASY("Fantasy"),
    SCIENCE_FICTION("Science Fiction"),
    BIOGRAPHY("Biography"),
    AUTOBIOGRAPHY("Autobiography"),
    HISTORY("History"),
    ROMANCE("Romance"),
    HORROR("Horror"),
    SELF_HELP("Self-help"),
    POETRY("Poetry"),
    CHILDREN("Children"),
    YOUNG_ADULT("Young Adult"),
    ADVENTURE("Adventure"),
    COOKING("Cooking"),
    TRAVEL("Travel"),
    HEALTH("Health"),
    ART("Art"),
    MUSIC("Music"),
    RELIGION("Religion"),
    SPIRITUALITY("Spirituality"),
    SCIENCE("Science"),
    PHILOSOPHY("Philosophy"),
    BUSINESS("Business"),
    EDUCATION("Education"),
    COMICS("Comics"),
    GRAPHIC_NOVEL("Graphic Novel"),
    DRAMA("Drama"),
    CLASSICS("Classics"),
    SPORTS("Sports"),
    TECHNOLOGY("Technology");
	
	private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
