package vkenutama.iot.coffeetime.Util

class Encryption {
    fun applyHash(password: String): String {
        val hashBuffer = charArrayOf(0x33.toChar(), 0x64.toChar(), 0x49.toChar(), 0x87.toChar(), 0x97.toChar())
        var salt = charArrayOf('g', 'o', 'l', '1', 'a', 'd', 'e', '#')
        var extendedPassword = password
        var hashedPassword = ""

        for (i in salt.indices) {
            salt[i] = (password[i % password.length].code or salt[i].code).toChar()
        }

        for (s in salt) {
            extendedPassword += s
        }

        for (i in extendedPassword.indices) {
            var charCode = extendedPassword[i].code and hashBuffer[i % hashBuffer.size].code
            charCode = 32 + (charCode % 95)
            hashedPassword += charCode.toChar()
        }

        return hashedPassword
    }
}