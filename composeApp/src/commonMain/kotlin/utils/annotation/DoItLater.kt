package utils.annotation

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.EXPRESSION,
    AnnotationTarget.LOCAL_VARIABLE
)
@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class DoItLater(val whatHaveToDo : String)
