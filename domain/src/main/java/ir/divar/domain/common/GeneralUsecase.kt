package ir.divar.domain.common

/**
 * This is a general usecase that is used for calling repository methods.
 *
 * @author Sepi 6/6/22
 */
abstract class GeneralUsecase<in Q : GeneralUsecase.RequestValue, R> {

    /**
     * Data that is passed to a request
     */
    interface RequestValue

    abstract suspend fun executeUseCase(requestValues: Q): R?
}