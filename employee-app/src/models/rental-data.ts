export interface Rental {
  id: number;
  dateAndTime: string;
  startLon: number;
  startLat: number;
  endLon: number;
  endLat: number;
  duration: number;
  clientId: number;
  clientName: string;
  vehicleId: string;
  vehicleModel: string;
  totalPrice: number;
}