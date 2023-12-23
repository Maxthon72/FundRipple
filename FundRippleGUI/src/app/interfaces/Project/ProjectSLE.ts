import { PartUser } from "../User/partUser";
import {Tag} from "./ProjectTags"

export interface ProjectSLE{
    projectName:string
    responsibleUser:PartUser
    goal:number
    moneyCollected:number
    numberOfSupporters:number
    dateCreated:string
    planedDateOfClosing:string
    summery:string
    tags:Tag[]
    bannerURL:string
    status:string
}