package primalcat.thaumcraft.client.renderer.overlay;

public class TextElement {
    private final String text;
    private float opacity;
    private boolean fadingIn;
    private boolean fadingOut;

    private static int color;

    public static int getColor() {
        return color;
    }

    public static void setColor(int color) {
        TextElement.color = color;
    }

    public TextElement(String text, int color) {
        this.text = text;
        this.opacity = 0.0F;
        this.fadingIn = true;
        this.fadingOut = false;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public boolean isFadingIn() {
        return fadingIn;
    }

    public void setFadingIn(boolean fadingIn) {
        this.fadingIn = fadingIn;
    }

    public boolean isFadingOut() {
        return fadingOut;
    }

    public void setFadingOut(boolean fadingOut) {
        this.fadingOut = fadingOut;
    }
}
