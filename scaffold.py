import os
import sys
import subprocess

def make_new_api(api_domain: str, api_name: str, api_method: str, mapping_path: str, has_request: bool, has_response: bool):
    register_presentation(api_domain, api_name, api_method, mapping_path, has_request, has_response)
    register_service(api_domain, api_name, has_request, has_response)

def write_code_in_file(file_path: str, codes: str):
    if not os.path.isfile(file_path):
        subprocess.run(['touch', file_path])

    master_key_file = open(file_path, 'w')
    master_key_file.write(codes)
    master_key_file.close()

def register_presentation(domain: str, api_name: str, method: str, path: str, has_request: bool, has_response: bool):
    method_str = ''
    if method == 'HEAD':
        method_str = f'@RequestMapping(value="{path}", method = RequestMethod.HEAD)'
    else:
        method_str = f'@{method.lower().capitalize()}Mapping("{path}")'

    param_str = ''
    if has_request:
        param_str = f'@Valid @RequestBody {api_name[0].lower() + api_name[1:]}RequestDto {api_name.capitalize()}RequestDto'
        req_str = f'''
data class {api_name.capitalize()}RequestDto()
'''
        write_file_at(f'src/main/kotlin/com/msg/gauth/domain/{domain}/presentation/dto/request/{api_name.capitalize()}RequestDto.kt', req_str)
    
    response_str = 'ResponseEntity<Void>'
    if has_response:
        response_str = f'ResponseEntity<{api_name.capitalize()}ResponseDto>'
        res_str = f'''
data class {api_name.capitalize()}ResponseDto()
'''
        write_file_at(f'src/main/kotlin/com/msg/gauth/domain/{domain}/presentation/dto/response/{api_name.capitalize()}ResponseDto.kt', res_str)

    controller_content = f"""    {method_str}
    fun {api_name[0].lower() + api_name[1:]}({param_str}): {response_str} {'{'}
        return ResponseEntity.ok().build()
    {'}'}

"""
    update_file_at(f'src/main/kotlin/com/msg/gauth/domain/{domain}/presentation/{domain.capitalize()}Controller.kt', find=') {', insert=controller_content)

def register_service(domain: str, api_name: str, has_request: bool, has_response: bool):
    param_str = ''
    if has_request:
        param_str = f'{api_name[0].lower() + api_name[1:]}RequestDto {api_name.capitalize()}RequestDto'
    
    response_str = ''
    if has_response:
        response_str = f': {api_name.capitalize()}ResponseDto'
    
    service_content = f"""@TransactionalService
class {api_name.capitalize()}Service(
) {'{'}
    fun execute({param_str}){response_str} {'{'}

    {'}'}
{'}'}
"""
    write_file_at(f'src/main/kotlin/com/msg/gauth/domain/{domain}/services/{api_name.capitalize()}Service.kt', service_content)

def read_file_at(file_path: str):
    with open(file_path, 'r') as file:
        return file.readlines()

def write_file_at(file_path: str, updated_content):
    os.makedirs(os.path.dirname(file_path), exist_ok=True)
    with open(file_path, 'w') as file:
        file.writelines(updated_content)

def update_file_at(file_path: str, find: str, insert: str):
    insert_line = 0
    read_file = read_file_at(file_path)
    target_file_len = len(read_file)
    for index, element in enumerate(read_file):
        if find in element:
            insert_line = index + 1
            break

    if target_file_len > int(insert_line):
        read_file.insert(insert_line, insert)
    
    write_file_at(file_path=file_path, updated_content=read_file)

print('Enter new api\'s domain name (lower) :\n', end='-> ', flush=True)
api_domain = sys.stdin.readline().replace('\n', '')

print('Enter new api name :\n', end='-> ', flush=True)
api_name = sys.stdin.readline().replace('\n', '')

print('Enter api\'s method :\n (GET, HEAD, POST, PUT, PATCH, DELETE)', end='-> ', flush=True)
api_method = sys.stdin.readline().replace('\n', '').upper()
if api_method not in ['GET', 'HEAD', 'POST', 'PUT', 'PATCH', 'DELETE']:
    print('Invalid method was entered')
    sys.exit()

print('Enter api\'s mapping :\n', end='-> ', flush=True)
mapping_path = sys.stdin.readline().replace('\n', '')

print('Include RequestDTO? (Y or N, default = N) :\n', end='-> ', flush=True)
has_request_dto = sys.stdin.readline().replace('\n', '').upper() == "Y"

print('Include ResponseDTO? (Y or N, default = N) :\n', end='-> ', flush=True)
has_response_dto = sys.stdin.readline().replace('\n', '').upper() == "Y"

print(f'Start to scaffold the new api named {api_name}...')

current_file_path = os.path.dirname(os.path.abspath(__file__))
os.chdir(current_file_path)
root_path = os.getcwd()

make_new_api(api_domain=api_domain, api_name=api_name, mapping_path=mapping_path, api_method=api_method, has_request=has_request_dto, has_response=has_response_dto)
