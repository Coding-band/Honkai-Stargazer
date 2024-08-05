package utils.calculator

import utils.annotation.DoItLater

@DoItLater("tmr continue")

fun getCharRange(score : Float) : String {
    if (score < 20) { return "D" }
    else if (score < 40) { return "C" }
    else if (score < 60) { return "B" }
    else if (score < 80) { return "A" }
    else if (score < 100) { return "S" }
    return "SS"

}