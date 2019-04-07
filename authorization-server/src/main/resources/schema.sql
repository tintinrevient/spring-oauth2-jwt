DROP TABLE IF EXISTS oauth_client_details;
create table oauth_client_details (
  client_id VARCHAR(128) PRIMARY KEY,
  resource_ids VARCHAR(128),
  client_secret VARCHAR(128),
  scope VARCHAR(128),
  authorized_grant_types VARCHAR(128),
  web_server_redirect_uri VARCHAR(128),
  authorities VARCHAR(128),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(128)
);