import { Vehicle } from "./vehicles-data";

export interface Car extends Vehicle {
  purchaseDate: string;
  description: string;
}