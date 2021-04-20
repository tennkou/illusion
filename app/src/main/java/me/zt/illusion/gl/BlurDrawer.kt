package me.zt.illusion.gl

class BlurDrawer constructor(val first: Boolean, val radius: Float,
                             val blurW: Int, val blurH: Int) : BitmapDrawer() {

    override fun onDraw() {
        super.onDraw()
        drawVertex()
    }

    override fun getFragmentShader(): String =
        if (first) {
            mFragmentShader1Pass
        } else {
            mFragmentShader2Pass
        }

    private val mFragmentShader1Pass = "precision highp float;\n" +
            "varying highp vec2 textureCoordinate;\n" +
            "uniform sampler2D inputImageTexture;\n" +
            blurHorizontally() + "\n" +
            "void main() {\n" +
            "\tvec2 uv = textureCoordinate;\n" +
            "\tgl_FragColor = BlurH(inputImageTexture, uv, $radius);\n" +
            "}"

    private val mFragmentShader2Pass = "precision highp float;\n" +
            "varying highp vec2 textureCoordinate;\n" +
            "uniform sampler2D inputImageTexture;\n" +
            blurVertically() + "\n" +
            "void main() {\n" +
            "\tvec2 uv = textureCoordinate;\n" +
            "\tgl_FragColor = BlurV(inputImageTexture, uv, $radius);\n" +
            "}"

    private fun blurVertically() =
        """float SCurve (float x) {
                x = x * 2.0 - 1.0;
                return -x * abs(x) * 0.5 + x + 0.5;
                }
                vec4 BlurV(sampler2D source, vec2 uv, float radius) {
                if (radius >= 1.0) {
                vec4 A = vec4(0.0); 
                vec4 C = vec4(0.0); 
                float width = ${1.0 / blurH};
                float divisor = 0.0; 
                        float weight = 0.0;
                        float radiusMultiplier = 1.0 / radius;
                for (float y = -radius; y <= radius; y++) {
                A = texture2D(source, uv + vec2(0.0, y * width));      
                            weight = SCurve(1.0 - (abs(y) * radiusMultiplier));         
                            C += A * weight;         
                divisor += weight; 
                }
                return vec4(C.r / divisor, C.g / divisor, C.b / divisor, 1.0);
                }
                return texture2D(source, uv);
                }"""

    private fun blurHorizontally() =
        """float SCurve (float x) {
    x = x * 2.0 - 1.0;
    return -x * abs(x) * 0.5 + x + 0.5;
}
                
vec4 BlurH(sampler2D source, vec2 uv, float radius) {
    if (radius >= 1.0) {
        vec4 A = vec4(0.0); 
        vec4 C = vec4(0.0); 
        float width = ${1.0 / blurW};
        float divisor = 0.0; 
        float weight = 0.0;
        float radiusMultiplier = 1.0 / radius;
        for (float x = -radius; x <= radius; x++) {
            A = texture2D(source, uv + vec2(x * width, 0.0));      
            weight = SCurve(1.0 - (abs(x) * radiusMultiplier));         
            C += A * weight;         
            divisor += weight; 
        }
        return vec4(C.r / divisor, C.g / divisor, C.b / divisor, 1.0);
    }
    return texture2D(source, uv);
}"""

}