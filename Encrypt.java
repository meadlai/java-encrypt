package com.meadlai.des;

import com.meadlai.des.implement.DES;

public class Encrypt extends DES {
	public void hsEncrypt(char[] key, char[] s1, char[] s2) {
		char key2[] = new char[16], tmp1[] = new char[16], tmp2[] = new char[16];
		char tmpKey[] = new char[16];
		int i;

		if (s1.length > 16)
			return;
		if (key.length > 15)
			return;

		for (int m = 0; m < key.length; m++) {
			tmpKey[m] = key[m];
		}
		make2key(tmpKey, key2);
		des2key(key2, true);
		for (int m = 0; m < s1.length; m++) {
			tmp1[m] = s1[m];
		}

		D2des(tmp1, tmp2);
		System.err.println("****************hsEncrypt=");
		System.err.println("****************hsEncrypt=");
		System.err.println("****************hsEncrypt=");

		for (i = 0; i < 16; i++) {
			System.err.print(0 + tmp2[i]);
			System.err.print(",");
			s2[i * 2] = (char) (tmp2[i] / 16 + ((tmp2[i] / 16 >= 10) ? ('A' - 10)
					: '0'));
			s2[i * 2 + 1] = (char) (tmp2[i] % 16 + ((tmp2[i] % 16 >= 10) ? ('A' - 10)
					: '0'));
		}
		s2[32] = '\0';
	}

	public void hsDecrypt(char[] key, char[] s1, char[] s2) {
		char key2[] = new char[16], tmp1[] = new char[16], tmp2[] = new char[16];
		char tmpKey[] = new char[16];
		int i;

		if (s1.length != 32)
			return;
		if (key.length > 15)
			return;

		for (int m = 0; m < key.length; m++) {
			tmpKey[m] = key[m];
		}
		make2key(tmpKey, key2);
		
		des2key(key2, false);
		for (int m = 0; m < s1.length; m++) {
			tmp1[m] = s1[m];
		}

		D2des(tmp1, tmp2);
		
		
		System.err.println("****************hsEncrypt=");
		System.err.println("****************hsEncrypt=");
		System.err.println("****************hsEncrypt=");

		
		s2[32] = '\0';
	}

	public static void main(String args[]) {
		Encrypt e = new Encrypt();
		char[] key = new char[] { 'O', 'N', 'W', 'F', '2', 'Y', 'I', 'E', 'V',
				'3', '4', 'I', 'S', 'B', 'L' };
		char[] text = new char[] { 't', 't', 't', 't', 't', '-' };
		char[] results = new char[33];
		e.hsEncrypt(key, text, results);
		System.err.println("=================");
		System.err.println("=================");
		for (int i = 0; i < results.length; i++) {
			System.err.print(results[i]);
		}
		String encrypttext = "E4A01459DA0D4B27EC1B5804E64FB921";
		e.hsDecrypt(key, s1, s2)

	}
}
