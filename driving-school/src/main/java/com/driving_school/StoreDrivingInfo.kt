
const val drivingSubjectOneKey:String = "driving_subject_one"
const val drivingSubjectFourKey:String = "driving_subject_four"

fun putDrivingSubjectOne(value:String){
    StoreManager.putData(drivingSubjectOneKey,value)
}

fun getDrivingSubjectOne():String{
    return StoreManager.getData(drivingSubjectOneKey,"")?:""
}

fun putDrivingSubjectFour(value:String){
    StoreManager.putData(drivingSubjectFourKey,value)
}

fun getDrivingSubjectFour():String{
    return StoreManager.getData(drivingSubjectFourKey,"")?:""
}