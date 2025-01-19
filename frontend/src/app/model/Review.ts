export interface Review {
	id: number
	id_product: number
	id_user: number
	rating: number
	short_note: string
	note: string
	date: Date
	last_modified: Date
}

export interface ReviewUsername {
	id: number
	id_product: number
	id_user: number
	username: string
	rating: number
	short_note: string
	note: string
	date: Date
	last_modified: Date
}

export interface ReviewInsert {
	id_product: number
	id_user: number
	rating: number
	short_note: string
	note: string
}

export interface ReviewUpdate {
	id: number
	rating: number
	shortNote: string
	note: string
}
