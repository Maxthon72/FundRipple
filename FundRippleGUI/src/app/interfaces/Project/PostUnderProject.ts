export interface PostUnderProjectRead{
    id:number
    post:string
    url:string
    likes:number
    dateCreated:string
}

export interface PostUnderProjectWrite{
    post:string
    url:string
}