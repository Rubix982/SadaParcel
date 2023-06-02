/*
 * Copyright 2023, Saif Ul Islam @ SadaParcel, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

INSERT INTO items (id, item_id, name, description, price, cost, order_id)
VALUES (1, '1', 'Item 1', 'Description for Item 1', 10.0, 5.0, NULL),
       (2, '2', 'Item 2', 'Description for Item 2', 15.0, 7.5, NULL),
       (3, '3', 'Item 3', 'Description for Item 3', 20.0, 10.0, NULL),
       (4, '4', 'Item 4', 'Description for Item 4', 25.0, 12.5, NULL),
       (5, '5', 'Item 5', 'Description for Item 5', 30.0, 15.0, NULL),
       (6, '6', 'Item 6', 'Description for Item 6', 35.0, 17.5, NULL),
       (7, '7', 'Item 7', 'Description for Item 7', 40.0, 20.0, NULL),
       (8, '8', 'Item 8', 'Description for Item 8', 45.0, 22.5, NULL),
       (9, '9', 'Item 9', 'Description for Item 9', 50.0, 25.0, NULL),
       (10, '10', 'Item 10', 'Description for Item 10', 55.0, 27.5, NULL);

INSERT INTO offers (id, offer_id, item_id, name, description, price_reduction, quantity_threshold, order_id)
VALUES (1, '1', '1', 'Offer 1', 'Description for Offer 1', 5.0, 2, NULL),
       (2, '2', '2', 'Offer 2', 'Description for Offer 2', 7.5, 3, NULL),
       (3, '3', '3', 'Offer 3', 'Description for Offer 3', 10.0, 4, NULL),
       (4, '4', '4', 'Offer 4', 'Description for Offer 4', 12.5, 5, NULL),
       (5, '5', '5', 'Offer 5', 'Description for Offer 5', 15.0, 6, NULL),
       (6, '6', '6', 'Offer 6', 'Description for Offer 6', 17.5, 7, NULL),
       (7, '7', '7', 'Offer 7', 'Description for Offer 7', 20.0, 8, NULL),
       (8, '8', '8', 'Offer 8', 'Description for Offer 8', 22.5, 9, NULL),
       (9, '9', '9', 'Offer 9', 'Description for Offer 9', 25.0, 10, NULL),
       (10, '10', '10', 'Offer 10', 'Description for Offer 10', 27.5, 11, NULL);