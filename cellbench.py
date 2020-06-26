import sys
import json


class Nameable():
    def __init__(self, name: str):
        self._name = name

    def name(self) -> str:
        return self._name


class Capability(Nameable):
    def __init__(self, name: str, categories: list = None):
        Nameable.__init__(self, name)
        self._categories = categories if categories is not None else []

    def categories(self) -> list:
        return self._categories


class Application(Nameable):
    def __init__(self, name: str, required_capabilities: list = None, required_categories: list = None):
        Nameable.__init__(self, name)
        self._required_capabilities = required_capabilities if required_capabilities is not None else []
        self._required_categories = required_categories if required_categories is not None else []

    def required_capabilities(self) -> list:
        return self._required_capabilities

    def required_categories(self) -> list:
        return self._required_categories


class Cellphone(Nameable):
    def __init__(self, name: str, capabilities: list = None):
        Nameable.__init__(self, name)
        self._capabilities = capabilities if capabilities is not None else []

    def capabilities(self) -> list:
        return self._capabilities
    
    def has_capability(self, name: str) -> bool:
        for capability in self.capabilities():
            if capability.name() == name:
                return True
        return False

    def has_category(self, name: str) -> bool:
        for capability in self.capabilities():
            for category in capability.categories():
                if category == name:
                    return True
        return False

    def supports_application(self, app: Application) -> bool:
        for category in app.required_categories():
            if not self.has_category(category):
                return False
        for capability in app.required_capabilities():
            if not self.has_capability(capability):
                return False
        return True


class Benchmark():
    def __init__(self, cellphone: Cellphone, applications: list):
        self._cellphone = cellphone
        self._applications = applications

    def cellphone(self) -> Cellphone:
        return self._cellphone

    def applications(self) -> list:
        return self._applications

    def run(self):
        results = {}
        for app in self.applications():
            results[app.name()] = self._cellphone.supports_application(app)
        return results


class OutputPrinter():
    def __init__(self):
        pass

    def print(self, msg: str = ""):
        print(msg)


class CellphonesData():
    def __init__(self):
        self._capabilities = []
        self._cellphones = []
        self._applications = []

    def capabilities(self):
        return self._capabilities

    def cellphones(self):
        return self._cellphones

    def applications(self):
        return self._applications

    def load(self, file) -> bool:
        data = {}
        try:
            data_file = open(file, 'r')
            data = json.load(data_file)
            data_file.close()
        except IOError:
            return False

        if 'capabilities' in data:
            for capability in data['capabilities']:
                if 'name' in capability and 'categories' in capability:
                    self._capabilities.append(Capability(capability['name'], capability['categories']))
        
        if 'cellphones' in data:
            for cellphone in data['cellphones']:
                if 'name' in cellphone and 'capabilities' in cellphone:
                    self._cellphones.append(Cellphone(cellphone['name'], self._capabilities_with_names(cellphone['capabilities'])))
        
        if 'applications' in data:
            for app in data['applications']:
                if 'name' in app:
                    required_capabilities = []
                    required_categories = []
                    if 'required_capabilities' in app:
                        required_capabilities = app['required_capabilities']
                    if 'required_categories' in app:
                        required_categories = app['required_categories']
                    if len(required_capabilities) or len(required_categories):
                        self._applications.append(Application(app['name'], required_capabilities, required_categories))

        return True

    def _capabilities_with_names(self, names: list) -> list:
        capabilities = []
        for capability in names:
            for existing_capability in self._capabilities:
                if capability == existing_capability.name():
                    capabilities.append(existing_capability)
        return capabilities


class BenchmarkRunner():
    def __init__(self, data: CellphonesData, printer: OutputPrinter = OutputPrinter()):
        self._data = data
        self._printer = printer

    def run(self):
        for cellphone in self._data.cellphones():
            self._printer.print('Running ' + cellphone.name() + ' benchmark:')
            results = Benchmark(cellphone, self._data.applications()).run()
            longest = max(map(len, results.items()))
            for app, supported in results.items():
                self._printer.print(('Supports ' + app).rjust(longest) + ': ' + ('O' if supported else 'X'))
            self._printer.print()


if __name__ == '__main__':
    cd = CellphonesData()
    if len(sys.argv) and cd.load(sys.argv[1]):
        BenchmarkRunner(cd).run()
