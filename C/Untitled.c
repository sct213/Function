typedef unsigned char byte;
void rijndael_mixcolumn (byte * data, size_t data_len) {
	if ((data_len % 4) != 0)
		return;

	for (size_t i = 0 ; i < data_len ; i += 4) {
		byte copy_arr [4], res [4];

		memcpy (copy_arr, data + i, 4);

		res [0] = (data [0 + i] << 1) ^ (0x1B & ((byte) ((signed char) data [0 + i] >> 7)));
		res [1] = (data [1 + i] << 1) ^ (0x1B & ((byte) ((signed char) data [1 + i] >> 7)));
		res [2] = (data [2 + i] << 1) ^ (0x1B & ((byte) ((signed char) data [2 + i] >> 7)));
		res [3] = (data [3 + i] << 1) ^ (0x1B & ((byte) ((signed char) data [3 + i] >> 7)));

		data [0 + i] = res [0] ^ copy_arr [3] ^ copy_arr [2] ^ res [1] ^ copy_arr [1];
		data [1 + i] = res [1] ^ copy_arr [0] ^ copy_arr [3] ^ res [2] ^ copy_arr [2];
		data [2 + i] = res [2] ^ copy_arr [1] ^ copy_arr [0] ^ res [3] ^ copy_arr [3];
		data [3 + i] = res [3] ^ copy_arr [2] ^ copy_arr [1] ^ res [0] ^ copy_arr [0];
	}
}