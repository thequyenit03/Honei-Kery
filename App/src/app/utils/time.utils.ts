export class TimeUtils {
  public static convertTimeStringToTimeObject(dateString: string): Date {
    if(!dateString) return new Date();
    return new Date(dateString);
  }

  public static convertTimeObjectToTimeStringFormatDDMMYYY(date: Date): string {
    if (!date) return '';
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
  }

  public static convertTimeObjectToTimeStringFormatYYYMMDD(date: Date): string {
    if (!date) return '';
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${year}-${month}-${day}`;
  }
}