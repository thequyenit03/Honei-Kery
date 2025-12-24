import * as CryptoJS from 'crypto-js';

export class DataUtils {
  public static encryptUsingAES256(value: any) {
    let _key = CryptoJS.enc.Utf8.parse('FFTCODESECURITY#');
    let _iv = CryptoJS.enc.Utf8.parse('FFTCODESECURITY#');
    let encrypted = CryptoJS.AES.encrypt(value.toString(), _key, {
      iv: _iv,
      mode: CryptoJS.mode.CBC,
    });
    return encrypted.toString();
  }

  public static decryptUsingAES256(text: string): string {
    let _key = CryptoJS.enc.Utf8.parse('FFTCODESECURITY#');
    let _iv = CryptoJS.enc.Utf8.parse('FFTCODESECURITY#');
    return CryptoJS.AES.decrypt(text, _key, {
      iv: _iv,
      mode: CryptoJS.mode.CBC,
    }).toString(CryptoJS.enc.Utf8);
  }
}