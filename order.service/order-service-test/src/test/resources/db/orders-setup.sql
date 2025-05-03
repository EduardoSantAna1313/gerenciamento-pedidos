DELETE FROM public.items WHERE 1 = 1;
DELETE FROM public.orders WHERE 1 = 1;

INSERT INTO public.orders
(total, created, updated, id, created_by, status, updated_by)
VALUES(0.00, NOW(), NOW(), uuid_generate_v4(), 'test', 'PENDING', 'test');

INSERT INTO public.orders
(total, created, updated, id, created_by, status, updated_by)
VALUES(0.00, NOW(), NOW(), uuid_generate_v4(), 'test', 'PENDING', 'test');

INSERT INTO public.orders
(total, created, updated, id, created_by, status, updated_by)
VALUES(0.00, NOW(), NOW(), uuid_generate_v4(), 'test', 'PENDING', 'test');