-- key
local leaky_bucket_key = KEYS[1]
-- 容量
local capacity = tonumber(ARGV[2])
-- 速率
local rate = tonumber(ARGV[1])
