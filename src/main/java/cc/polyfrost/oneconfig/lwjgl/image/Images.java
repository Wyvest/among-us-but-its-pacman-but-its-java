package cc.polyfrost.oneconfig.lwjgl.image;

/**
 * An enum of images used in OneConfig.
 *
 * @see cc.polyfrost.oneconfig.lwjgl.RenderManager#drawImage(long, String, float, float, float, float, int)
 * @see ImageLoader
 */
public enum Images {
    PACMAN_CLOSED("/assets/oneconfig/sprites/pacman_closed.png"),
    PACMAN_OPEN("/assets/oneconfig/sprites/pacman_open.png"),
    HELTER_SKELTER("/assets/oneconfig/sprites/HELTER_SKELTER.png"); // 9

    public final String filePath;

    Images(String filePath) {
        this.filePath = filePath;
    }
}