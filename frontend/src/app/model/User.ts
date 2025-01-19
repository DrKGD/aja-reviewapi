export interface UserLogin {
	username: string
	password: string
}

export interface UserRegistration {
	username: string
	password: string
	email:		string
}

export interface UserAuth {
	username?: string
	role: "user" | "editor" | "guest"
	id?: number
}
