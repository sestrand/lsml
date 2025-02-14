/*
 * Li Song Mechlab - A 'mech building tool for PGI's MechWarrior: Online.
 * Copyright (C) 2013-2023  Li Song
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lisoft.lsml.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import org.junit.Test;

/**
 * This is an integration test between {@link Base64} and {@link Huffman1} simply to see that they
 * will play nice with each other.
 *
 * @author Li Song
 */
public class Base64HuffmanStringTest {
  /**
   * This will test if a String object will survive being compressed with Huffman, the output
   * encoded with base64 and then decompressed.
   */
  @Test
  public void testEncodeDecode() throws DecodingException, EncodingException {
    // Setup
    final String input =
        "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    final List<Character> i = new ArrayList<>();
    final Map<Character, Integer> freq = new TreeMap<>();
    for (final char c : input.toCharArray()) {
      i.add(c);
      if (freq.containsKey(c)) {
        freq.put(c, freq.get(c) + 1);
      } else {
        freq.put(c, 1);
      }
    }

    // Execute
    final Huffman1<Character> huff = new Huffman1<>(freq, '\0');
    final Encoder base64Encoder = Base64.getEncoder();
    final Decoder base64Decoder = Base64.getDecoder();

    final byte[] encoded = base64Encoder.encode(huff.encode(i));
    final List<Character> o = huff.decode(base64Decoder.decode(encoded));

    // Verify
    assertArrayEquals(i.toArray(), o.toArray());
    assertTrue(
        "Encoded length: "
            + encoded.length
            + " bytes, source length: "
            + input.length()
            + " bytes.",
        encoded.length < input.length() * 0.7);
  }
}
