package nonthpawit.borntodev.hackhack

class Users {
    var firstName: String? = null
    var lastName: String? = null
    var age: String? = null
    var userName: String? = null

    constructor() {}
    constructor(firstName: String?, lastName: String?, age: String?, userName: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.age = age
        this.userName = userName
    }
}