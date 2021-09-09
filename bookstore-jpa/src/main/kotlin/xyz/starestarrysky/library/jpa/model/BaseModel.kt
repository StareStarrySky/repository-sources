package xyz.starestarrysky.library.jpa.model

import java.io.Serializable
import javax.persistence.MappedSuperclass

@MappedSuperclass
interface BaseModel : Serializable {
    interface BaseView

    interface ListView : BaseView

    interface DetailView : ListView

    interface AddCheck

    interface UpdateCheck
}
