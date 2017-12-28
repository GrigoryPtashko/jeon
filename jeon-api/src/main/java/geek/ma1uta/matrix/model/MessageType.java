package geek.ma1uta.matrix.model;

public enum MessageType implements EnumWithCode {

    TEXT("m.text"),
    EMOTE("m.emote"),
    NOTICE("m.notice"),
    IMAGE("m.image"),
    FILE("m.file"),
    LOCATION("m.location"),
    VIDEO("m.video"),
    AUDIO("m.audio");

    private String code;

    MessageType(String code) {
        this.code = code;
    }

    @Override
    public String code() {
        return code;
    }
}
