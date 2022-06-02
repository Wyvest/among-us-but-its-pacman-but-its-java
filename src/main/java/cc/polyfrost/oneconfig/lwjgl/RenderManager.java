package cc.polyfrost.oneconfig.lwjgl;

import cc.polyfrost.oneconfig.lwjgl.font.Font;
import cc.polyfrost.oneconfig.lwjgl.font.Fonts;
import cc.polyfrost.oneconfig.lwjgl.image.ImageLoader;
import cc.polyfrost.oneconfig.lwjgl.image.Images;
import cc.polyfrost.oneconfig.lwjgl.image.SVGs;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Handles NanoVG rendering and wraps it in a more convenient interface.
 */
public final class RenderManager {
    private static long vg = -1;

    //nanovg

    private RenderManager() {

    }

    /**
     * Draws a rectangle with the given parameters.
     *
     * @param vg     The NanoVG context.
     * @param x      The x position.
     * @param y      The y position.
     * @param width  The width.
     * @param height The height.
     * @param color  The color.
     */
    public static void drawRect(long vg, float x, float y, float width, float height, int color) {
        nvgBeginPath(vg);
        nvgRect(vg, x, y, width, height);
        NVGColor nvgColor = color(vg, color);
        nvgFill(vg);
        nvgColor.free();
    }

    /**
     * Draws a rounded rectangle with the given parameters.
     *
     * @param vg     The NanoVG context.
     * @param x      The x position.
     * @param y      The y position.
     * @param width  The width.
     * @param height The height.
     * @param color  The color.
     * @param radius The radius.
     */
    public static void drawRoundedRect(long vg, float x, float y, float width, float height, int color, float radius) {
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, width, height, radius);
        color(vg, color);
        NVGColor nvgColor = color(vg, color);
        nvgFill(vg);
        nvgColor.free();
    }

    /**
     * Draw a rounded rectangle where every corner has a different radius
     *
     * @param vg       The NanoVG context
     * @param x        The x position.
     * @param y        The y position.
     * @param width    The width.
     * @param height   The height.
     * @param color    The color.
     * @param radiusTL Top left corner radius.
     * @param radiusTR Top right corner radius.
     * @param radiusBR Bottom right corner radius.
     * @param radiusBL Bottom left corner radius
     */
    public static void drawRoundedRectVaried(long vg, float x, float y, float width, float height, int color, float radiusTL, float radiusTR, float radiusBR, float radiusBL) {
        nvgBeginPath(vg);
        nvgRoundedRectVarying(vg, x, y, width, height, radiusTL, radiusTR, radiusBR, radiusBL);
        color(vg, color);
        NVGColor nvgColor = color(vg, color);
        nvgFill(vg);
        nvgColor.free();
    }

    /**
     * Draws a hollow rounded rectangle with the given parameters.
     *
     * @param vg        The NanoVG context.
     * @param x         The x position.
     * @param y         The y position.
     * @param width     The width.
     * @param height    The height.
     * @param color     The color.
     * @param radius    The radius.
     * @param thickness The thickness.
     */
    public static void drawHollowRoundRect(long vg, float x, float y, float width, float height, int color, float radius, float thickness) {
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x + thickness, y + thickness, width - thickness, height - thickness, radius);
        nvgStrokeWidth(vg, thickness + 0.5f);
        nvgPathWinding(vg, NVG_HOLE);
        color(vg, color);
        NVGColor nvgColor = color(vg, color);
        nvgStrokeColor(vg, nvgColor);
        nvgStroke(vg);
        nvgColor.free();
    }

    /**
     * Draws a gradient rectangle with the given parameters.
     *
     * @param vg     The NanoVG context.
     * @param x      The x position.
     * @param y      The y position.
     * @param width  The width.
     * @param height The height.
     * @param color  The first color of the gradient.
     * @param color2 The second color of the gradient.
     */
    public static void drawGradientRect(long vg, float x, float y, float width, float height, int color, int color2) {
        NVGPaint bg = NVGPaint.create();
        nvgBeginPath(vg);
        nvgRect(vg, x, y, width, height);
        NVGColor nvgColor = color(vg, color);
        NVGColor nvgColor2 = color(vg, color2);
        nvgFillPaint(vg, nvgLinearGradient(vg, x, y, x, y + width, nvgColor, nvgColor2, bg));
        nvgFillPaint(vg, bg);
        nvgFill(vg);
        nvgColor.free();
        nvgColor2.free();
    }

    /**
     * Draws a rounded gradient rectangle with the given parameters.
     *
     * @param vg     The NanoVG context.
     * @param x      The x position.
     * @param y      The y position.
     * @param width  The width.
     * @param height The height.
     * @param color  The first color of the gradient.
     * @param color2 The second color of the gradient.
     * @param radius The corner radius.
     */
    public static void drawGradientRoundedRect(long vg, float x, float y, float width, float height, int color, int color2, float radius) {
        NVGPaint bg = NVGPaint.create();
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, width, height, radius);
        NVGColor nvgColor = color(vg, color);
        NVGColor nvgColor2 = color(vg, color2);
        nvgFillPaint(vg, nvgLinearGradient(vg, x, y, x + width, y, nvgColor, nvgColor2, bg));
        nvgFill(vg);
        nvgColor.free();
        nvgColor2.free();
    }

    /**
     * Draws a circle with the given parameters.
     *
     * @param vg     The NanoVG context.
     * @param x      The x position.
     * @param y      The y position.
     * @param radius The radius.
     * @param color  The color.
     */
    public static void drawCircle(long vg, float x, float y, float radius, int color) {
        nvgBeginPath(vg);
        nvgCircle(vg, x, y, radius);
        NVGColor nvgColor = color(vg, color);
        nvgFill(vg);
        nvgColor.free();
    }

    /**
     * Draws a String with the given parameters.
     *
     * @param vg    The NanoVG context.
     * @param text  The text.
     * @param x     The x position.
     * @param y     The y position.
     * @param color The color.
     * @param size  The size.
     * @param font  The font.
     * @see Font
     */
    public static void drawText(long vg, String text, float x, float y, int color, float size, Fonts font) {
        drawText(vg, text, x, y, color, size, font.font);
    }

    /**
     * Draws a String with the given parameters.
     *
     * @param vg    The NanoVG context.
     * @param text  The text.
     * @param x     The x position.
     * @param y     The y position.
     * @param color The color.
     * @param size  The size.
     * @param font  The font.
     * @see Font
     */
    public static void drawText(long vg, String text, float x, float y, int color, float size, Font font) {
        nvgBeginPath(vg);
        nvgFontSize(vg, size);
        nvgFontFace(vg, font.getName());
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);
        NVGColor nvgColor = color(vg, color);
        nvgText(vg, x, y, text);
        nvgFill(vg);
        nvgColor.free();
    }

    /**
     * Draws a String wrapped at the given width, with the given parameters.
     *
     * @param vg    The NanoVG context.
     * @param text  The text.
     * @param x     The x position.
     * @param y     The y position.
     * @param width The width.
     * @param color The color.
     * @param size  The size.
     * @param font  The font.
     */
    public static void drawWrappedString(long vg, String text, float x, float y, float width, int color, float size, Fonts font) {
        drawWrappedString(vg, text, x, y, width, color, size, font.font);
    }

    /**
     * Draws a String wrapped at the given width, with the given parameters.
     *
     * @param vg    The NanoVG context.
     * @param text  The text.
     * @param x     The x position.
     * @param y     The y position.
     * @param width The width.
     * @param color The color.
     * @param size  The size.
     * @param font  The font.
     */
    public static void drawWrappedString(long vg, String text, float x, float y, float width, int color, float size, Font font) {
        nvgBeginPath(vg);
        nvgFontSize(vg, size);
        nvgFontFace(vg, font.getName());
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);
        NVGColor nvgColor = color(vg, color);
        nvgTextBox(vg, x, y, width, text);
        nvgFill(vg);
        nvgColor.free();
    }

    /**
     * Draws an image with the provided file path.
     *
     * @param vg       The NanoVG context.
     * @param filePath The file path.
     * @param x        The x position.
     * @param y        The y position.
     * @param width    The width.
     * @param height   The height.
     * @see RenderManager#drawImage(long, String, float, float, float, float, int)
     */
    public static void drawImage(long vg, String filePath, float x, float y, float width, float height) {
        if (ImageLoader.INSTANCE.loadImage(vg, filePath)) {
            NVGPaint imagePaint = NVGPaint.calloc();
            int image = ImageLoader.INSTANCE.getImage(filePath);
            nvgBeginPath(vg);
            nvgImagePattern(vg, x, y, width, height, 0, image, 1, imagePaint);
            nvgRect(vg, x, y, width, height);
            nvgFillPaint(vg, imagePaint);
            nvgFill(vg);
            imagePaint.free();
        }
    }

    /**
     * Draws an image with the provided file path.
     *
     * @param vg       The NanoVG context.
     * @param filePath The file path.
     * @param x        The x position.
     * @param y        The y position.
     * @param width    The width.
     * @param height   The height.
     * @param color    The color.
     */
    public static void drawImage(long vg, String filePath, float x, float y, float width, float height, int color) {
        if (ImageLoader.INSTANCE.loadImage(vg, filePath)) {
            NVGPaint imagePaint = NVGPaint.calloc();
            int image = ImageLoader.INSTANCE.getImage(filePath);
            nvgBeginPath(vg);
            nvgImagePattern(vg, x, y, width, height, 0, image, 1, imagePaint);
            nvgRGBA((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF), (byte) (color >> 24 & 0xFF), imagePaint.innerColor());
            nvgRect(vg, x, y, width, height);
            nvgFillPaint(vg, imagePaint);
            nvgFill(vg);
            imagePaint.free();
        }
    }

    /**
     * Draws an image with the provided file path and parameters.
     *
     * @see RenderManager#drawImage(long, String, float, float, float, float)
     */
    public static void drawImage(long vg, Images filePath, float x, float y, float width, float height) {
        drawImage(vg, filePath.filePath, x, y, width, height);
    }

    /**
     * Draws an image with the provided file path and parameters.
     *
     * @see RenderManager#drawImage(long, String, float, float, float, float, int)
     */
    public static void drawImage(long vg, Images filePath, float x, float y, float width, float height, int color) {
        drawImage(vg, filePath.filePath, x, y, width, height, color);
    }

    /**
     * Draws a rounded image with the provided file path and parameters.
     *
     * @param vg       The NanoVG context.
     * @param filePath The file path.
     * @param x        The x position.
     * @param y        The y position.
     * @param width    The width.
     * @param height   The height.
     * @param radius   The radius.
     */
    public static void drawRoundImage(long vg, String filePath, float x, float y, float width, float height, float radius) {
        if (ImageLoader.INSTANCE.loadImage(vg, filePath)) {
            NVGPaint imagePaint = NVGPaint.calloc();
            int image = ImageLoader.INSTANCE.getImage(filePath);
            nvgBeginPath(vg);
            nvgImagePattern(vg, x, y, width, height, 0, image, 1, imagePaint);
            nvgRoundedRect(vg, x, y, width, height, radius);
            nvgFillPaint(vg, imagePaint);
            nvgFill(vg);
            imagePaint.free();
        }
    }

    /**
     * Draws a rounded image with the provided file path and parameters.
     *
     * @see RenderManager#drawRoundImage(long, String, float, float, float, float, float)
     */
    public static void drawRoundImage(long vg, Images filePath, float x, float y, float width, float height, float radius) {
        drawRoundImage(vg, filePath.filePath, x, y, width, height, radius);
    }

    public static float getTextWidth(long vg, String text, float fontSize, Fonts font) {
        return getTextWidth(vg, text, fontSize, font.font);
    }

    /**
     * Get the width of the provided String.
     *
     * @param vg       The NanoVG context.
     * @param text     The text.
     * @param fontSize The font size.
     * @param font     The font.
     * @return The width of the text.
     */
    public static float getTextWidth(long vg, String text, float fontSize, Font font) {
        float[] bounds = new float[4];
        nvgFontSize(vg, fontSize);
        nvgFontFace(vg, font.getName());
        return nvgTextBounds(vg, 0, 0, text, bounds);
    }

    /**
     * Draws a line with the provided parameters.
     *
     * @param vg    The NanoVG context.
     * @param x     The x position.
     * @param y     The y position.
     * @param endX  The end x position.
     * @param endY  The end y position.
     * @param width The width.
     * @param color The color.
     */
    public static void drawLine(long vg, float x, float y, float endX, float endY, float width, int color) {
        nvgBeginPath(vg);
        nvgMoveTo(vg, x, y);
        nvgLineTo(vg, endX, endY);
        NVGColor nvgColor = color(vg, color);
        nvgStrokeColor(vg, nvgColor);
        nvgStrokeWidth(vg, width);
        nvgStroke(vg);
        nvgColor.free();
    }

    /**
     * Draw a drop shadow.
     *
     * <a href="https://github.com/SpinyOwl/legui/blob/develop/LICENSE">Adapted from legui under MIT license</a>
     *
     * @param vg           The NanoVG context.
     * @param x            The x coordinate.
     * @param y            The y coordinate.
     * @param w            The width.
     * @param h            The height.
     * @param blur         The blur (feather).
     * @param spread       The spread.
     * @param cornerRadius The radius of the corner
     */
    public static void drawDropShadow(long vg, float x, float y, float w, float h, float blur, float spread, float cornerRadius) {
        try (NVGPaint shadowPaint = NVGPaint.calloc();  // allocating memory to pass color to nanovg wrapper
             NVGColor firstColor = NVGColor.calloc();  // allocating memory to pass color to nanovg wrapper
             NVGColor secondColor = NVGColor.calloc()  // allocating memory to pass color to nanovg wrapper
        ) {
            fillNVGColorWithRGBA(0, 0, 0, 0.5f, firstColor); // filling allocated memory
            fillNVGColorWithRGBA(0, 0, 0, 0, secondColor); // filling allocated memory

            // creating gradient and put it to shadowPaint
            nvgBoxGradient(vg, x - spread, y - spread, w + 2 * spread, h + 2 * spread, cornerRadius + spread, blur, firstColor, secondColor, shadowPaint);
            nvgBeginPath(vg);
            nvgRoundedRect(vg, x - spread - blur, y - spread - blur, w + 2 * spread + 2 * blur, h + 2 * spread + 2 * blur, cornerRadius + spread);
            nvgRoundedRect(vg, x, y, w, h, cornerRadius);
            nvgPathWinding(vg, NVG_HOLE);
            nvgFillPaint(vg, shadowPaint);
            nvgFill(vg);
        }
    }

    /**
     * Fills the provided {@link NVGColor} with the provided RGBA values.
     *
     * @param r     The red value.
     * @param g     The green value.
     * @param b     The blue value.
     * @param a     The alpha value.
     * @param color The {@link NVGColor} to fill.
     */
    public static void fillNVGColorWithRGBA(float r, float g, float b, float a, NVGColor color) {
        color.r(r);
        color.g(g);
        color.b(b);
        color.a(a);
    }

    /**
     * Create a {@link NVGColor} from the provided RGBA values.
     *
     * @param vg    The NanoVG context.
     * @param color The color.
     * @return The {@link NVGColor} created.
     */
    public static NVGColor color(long vg, int color) {
        NVGColor nvgColor = NVGColor.calloc();
        nvgRGBA((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF), (byte) (color >> 24 & 0xFF), nvgColor);
        nvgFillColor(vg, nvgColor);
        return nvgColor;
    }

    /**
     * Scales all rendering by the provided scale.
     *
     * @param vg The NanoVG context.
     * @param x  The x scale.
     * @param y  The y scale.
     */
    public static void scale(long vg, float x, float y) {
        nvgScale(vg, x, y);
    }

    public static void translate(long vg, float x, float y) {
        nvgTranslate(vg, x, y);
    }

    /**
     * Sets the global alpha value to render with.
     *
     * @param vg    The NanoVG context.
     * @param alpha The alpha value.
     */
    public static void setAlpha(long vg, float alpha) {
        nvgGlobalAlpha(vg, alpha);
    }

    /**
     * Draws a SVG with the provided file path and parameters.
     *
     * @param vg       The NanoVG context.
     * @param filePath The file path.
     * @param x        The x position.
     * @param y        The y position.
     * @param width    The width.
     * @param height   The height.
     */
    public static void drawSvg(long vg, String filePath, float x, float y, float width, float height) {
        float w = width;
        float h = height;
        /*/
        if (OneConfigGui.INSTANCE != null) {
            w *= OneConfigGui.INSTANCE.getScaleFactor();
            h *= OneConfigGui.INSTANCE.getScaleFactor();
        }

         */
        if (ImageLoader.INSTANCE.loadSVG(vg, filePath, w, h)) {
            NVGPaint imagePaint = NVGPaint.calloc();
            int image = ImageLoader.INSTANCE.getSVG(filePath, w, h);
            nvgBeginPath(vg);
            nvgImagePattern(vg, x, y, width, height, 0, image, 1, imagePaint);
            nvgRect(vg, x, y, width, height);
            nvgFillPaint(vg, imagePaint);
            nvgFill(vg);
            imagePaint.free();
        }
    }

    /**
     * Draws a SVG with the provided file path and parameters.
     *
     * @param vg       The NanoVG context.
     * @param filePath The file path.
     * @param x        The x position.
     * @param y        The y position.
     * @param width    The width.
     * @param height   The height.
     * @param color    The color.
     */
    public static void drawSvg(long vg, String filePath, float x, float y, float width, float height, int color) {
        float w = width;
        float h = height;
        /*
        if (OneConfigGui.INSTANCE != null) {
            w *= OneConfigGui.INSTANCE.getScaleFactor();
            h *= OneConfigGui.INSTANCE.getScaleFactor();
        }
         */
        if (ImageLoader.INSTANCE.loadSVG(vg, filePath, w, h)) {
            NVGPaint imagePaint = NVGPaint.calloc();
            int image = ImageLoader.INSTANCE.getSVG(filePath, w, h);
            nvgBeginPath(vg);
            nvgImagePattern(vg, x, y, width, height, 0, image, 1, imagePaint);
            nvgRGBA((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF), (byte) (color >> 24 & 0xFF), imagePaint.innerColor());
            nvgRect(vg, x, y, width, height);
            nvgFillPaint(vg, imagePaint);
            nvgFill(vg);
            imagePaint.free();
        }
    }

    /**
     * Draws an SVG with the provided file path and parameters.
     *
     * @see RenderManager#drawSvg(long, String, float, float, float, float)
     */
    public static void drawSvg(long vg, SVGs svg, float x, float y, float width, float height) {
        drawSvg(vg, svg.filePath, x, y, width, height);
    }

    /**
     * Draws an SVG with the provided file path and parameters.
     *
     * @see RenderManager#drawSvg(long, String, float, float, float, float, int)
     */
    public static void drawSvg(long vg, SVGs svg, float x, float y, float width, float height, int color) {
        drawSvg(vg, svg.filePath, x, y, width, height, color);
    }
}
