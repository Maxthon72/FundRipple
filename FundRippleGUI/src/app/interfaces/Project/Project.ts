import { PartUser } from "../User/partUser"
import { ProjectDescription } from "./ProjectDescription"
import { Tag } from "./ProjectTags"

export interface Project {
    projectName:string
    goal:number
    summery:string
    planedDateOfClosing:string
  }
export interface FullProject{
  projectName:string
  responsibleUser:PartUser
  goal:number
  moneyCollected:number
  numberOfSupporters:number
  dateCreated:string
  dateClosed:string|null
  planedDateOfClosing:string
  description:ProjectDescription[]
  summery:string
  tags:Tag[]
  bannerURL:string
}
