update utilisateur set agence_id = null;
delete from agence;

insert into agence(id,description,nom_agence) select distinct cast(c.id_agence as bigint), c.nom_agence, c.nom_agence from client c;

INSERT INTO public.parametrage_rapport (id, code, description, key_value, select_clause, types) VALUES (7, 'Obligations cautionnées de douaneDepots de garanties engagements HB', 'Ouverture de lettre de crédit', '[{"column":"glSubhead","value":"20601", "operation":"=", "operand":true},{"column":"mainProductDesc","value":"LAA", "operation":"LIKE", "operand":true}]', 'nominalExposure', 'Depots de garanties engagements HB');
INSERT INTO public.parametrage_rapport (id, code, description, key_value, select_clause, types) VALUES (8, 'AvalDepots de garanties engagements HB', 'Ouverture de lettre de crédit', '[{"column":"glSubhead","value":"20601", "operation":"=", "operand":true},{"column":"mainProductDesc","value":"LAA", "operation":"LIKE", "operand":true}]', 'nominalExposure', 'Depots de garanties engagements HB');
INSERT INTO public.parametrage_rapport (id, code, description, key_value, select_clause, types) VALUES (9, 'CautionsDepots de garanties engagements HB', 'Ouverture de lettre de crédit', '[{"column":"glSubhead","value":"20601", "operation":"=", "operand":true},{"column":"mainProductDesc","value":"LAA", "operation":"LIKE", "operand":true}]', 'nominalExposure', 'Depots de garanties engagements HB');
INSERT INTO public.parametrage_rapport (id, code, description, key_value, select_clause, types) VALUES (10, 'Ouverture de lettre de créditDepots de garanties engagements HB', 'Ouverture de lettre de crédit', '[{"column":"glSubhead","value":"20601", "operation":"=", "operand":true},{"column":"mainProductDesc","value":"LAA", "operation":"LIKE", "operand":true}]', 'nominalExposure', 'Depots de garanties engagements HB');
INSERT INTO public.parametrage_rapport (id, code, description, key_value, select_clause, types) VALUES (11, 'Ouverture de lettre de créditEngagement HB', 'Ouverture de lettre de crédit', '[{"column":"glSubhead","value":"20601", "operation":"=", "operand":true},{"column":"glSubhead","value":"28217", "operation":"=", "operand":false}]', 'nominalExposure', 'Engagement HB');
INSERT INTO public.parametrage_rapport (id, code, description, key_value, select_clause, types) VALUES (12, 'CautionsEngagement HB', 'Ouverture de lettre de crédit', '[{"column":"glSubhead","value":"20601", "operation":"=", "operand":true},{"column":"glSubhead","value":"28217", "operation":"=", "operand":false}]', 'nominalExposure', 'Engagement HB');
INSERT INTO public.parametrage_rapport (id, code, description, key_value, select_clause, types) VALUES (13, 'AvalEngagement HB', 'Ouverture de lettre de crédit', '[{"column":"glSubhead","value":"20601", "operation":"=", "operand":true},{"column":"glSubhead","value":"28217", "operation":"=", "operand":false}]', 'nominalExposure', 'Engagement HB');
INSERT INTO public.parametrage_rapport (id, code, description, key_value, select_clause, types) VALUES (14, 'Obligations cautionnées de douaneEngagement HB', 'Ouverture de lettre de crédit', '[{"column":"glSubhead","value":"20601", "operation":"=", "operand":true},{"column":"glSubhead","value":"28217", "operation":"=", "operand":false}]', 'nominalExposure', 'Engagement HB');



SELECT pg_catalog.setval('public.parametrage_rapport_id_seq', 14, true);

