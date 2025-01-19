export interface Product {
	id: number
	name: string
	description: string
	price: number
}

export interface ProductWithAVG {
	id: number
	name: string
	description: string
	average: number | null
	price: number
}
