package me.zt.illusion.gl

class BlurDrawer : VertexDrawer() {

    override fun getFragmentShader(): String =
        """float SCurve (float x) {
                x = x * 2.0 - 1.0;
                return -x * abs(x) * 0.5 + x + 0.5;
                }
                vec4 BlurV (sampler2D source, vec2 uv, float radius) {
                if (radius >= 1.0) {
                vec4 A = vec4(0.0); 
                vec4 C = vec4(0.0); 
                float width = 0.005;
                float divisor = 0.0; 
                        float weight = 0.0;
                        float radiusMultiplier = 1.0 / radius;
                for (float y = -radius; y <= radius; y++) {
                A = texture2D(source, uv + vec2(0.0, y * width));      
                            weight = SCurve(1.0 - (abs(y) * radiusMultiplier));         
                            C += A * weight;         
                divisor += weight; 
                }
                return vec4(C.r / divisor, C.g / divisor, C.b / divisor, 1.0);" +
                }
                return texture2D(source, uv);
                }"""

    private val blurHorizontalCode =
        """float SCurve (float x) {
                    x = x * 2.0 - 1.0;
                return -x * abs(x) * 0.5 + x + 0.5;
                }
                vec4 BlurH (sampler2D source, vec2 uv, float radius) {
                if (radius >= 1.0) {
                vec4 A = vec4(0.0); 
                vec4 C = vec4(0.0); 
                float width = 0.005;
                float divisor = 0.0; 
                        float weight = 0.0;
                        float radiusMultiplier = 1.0 / radius;
                for (float x = -radius; x <= radius; x++) {
                A = texture2D(source, uv + vec2(x * width, 0.0));      
                            weight = SCurve(1.0 - (abs(x) * radiusMultiplier));         
                            C += A * weight;         
                divisor += weight; 
                }
                return vec4(C.r / divisor, C.g / divisor, C.b / divisor, 1.0)
                }
                return texture2D(source, uv);
                }"""

}