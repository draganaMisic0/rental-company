export interface AddClientRequest {
    id: number;
    firstName: string;
    lastName: string;
    idNumber: string | null;
    email: string;
    phone: string;
    avatarUrl: string | null;
    username: string;
    passportNumber: string | null;
    driverLicenceNumber: string;
    citizenship: string;
    active: boolean;
    password: string;
}