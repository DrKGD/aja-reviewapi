export interface LoginResponse {
	kind: "OK" | "BAD_CREDENTIALS"
	message: string
}

export interface AuthResponse {
	kind: "AUTH" | "GUEST",
	username?: string
	role?: "user" | "editor"
	id?: number
}
